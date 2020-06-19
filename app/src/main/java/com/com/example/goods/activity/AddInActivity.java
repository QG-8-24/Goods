package com.com.example.goods.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.com.example.goods.BaseActivity;
import com.com.example.goods.R;
import com.com.example.goods.entity.Goods;
import com.com.example.goods.entity.Instorage;
import com.com.example.goods.utils.HttpUtil;
import com.com.example.goods.utils.JsonUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class AddInActivity extends BaseActivity implements View.OnClickListener {
    private EditText company;
    private EditText dept;
    private TextView type;
    private ArrayList<Goods> goodslist;
    private ImageView back;
    private EditText linkman;
    private EditText phone;
    private TextView goodsname;
    private EditText amount;
    private Button hold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_in);
        initView();
        initEvent();
    }

    @Override
    public void init() {

    }

    @Override
    public void initView() {
        back = findViewById(R.id.back);
        company = findViewById(R.id.company);
        dept = findViewById(R.id.department);
        type = findViewById(R.id.type);
        linkman = findViewById(R.id.linkman);
        phone = findViewById(R.id.phone);
        goodsname = findViewById(R.id.name);
        amount = findViewById(R.id.amount);
        hold = findViewById(R.id.hold);
    }

    @Override
    public void initData() {
    }

    @Override
    public void initEvent() {
        back.setOnClickListener(this);
        type.setOnClickListener(this);
        goodsname.setOnClickListener(this);
        hold.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.type:
                singleChoiceDialog_TYPE();
                break;
            case R.id.name:
                getAll();
                break;
            case R.id.hold:
                addIn();
                break;
        }
    }


    public void getAll() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String s = HttpUtil.postRequest("/goods/getall");
                    ArrayList<Goods> arrayList = JsonUtils.string2Object(s, ArrayList.class);
                    goodslist = new ArrayList<>();
                    for (Object o : arrayList) {
                        Goods goods = JsonUtils.string2Object(JsonUtils.object2String(o), Goods.class);
                        goodslist.add(goods);
                    }
                    Message message = Message.obtain();
                    message.what = 1;
                    myhandler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //UIHandler
    private Handler myhandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 1) {
                singleChoiceDialog_goods();
            } else if (msg.what == 2) {
                Toast.makeText(AddInActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddInActivity.this, InActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(AddInActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
            }

        }
    };


    /**
     * 单选对话框
     */
    public void singleChoiceDialog_TYPE() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddInActivity.this);
        final ArrayList<String> list = new ArrayList<>();
        builder.setTitle("请选择入库类型");

        final String[] strings = new String[]{ "1-无偿捐款", "2-上级下拨", "3-自行采购", "4-采购退货"};
        builder.setSingleChoiceItems(strings, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                type.setText(strings[which].toString());
            }
        });
        AlertDialog dialog = builder.create();  //创建AlertDialog对象
        dialog.show();                           //显示对话框
    }

    /**
     * 单选对话框
     */
    public void singleChoiceDialog_goods() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddInActivity.this);
        final ArrayList<String> list = new ArrayList<>();
        builder.setTitle("请选择入库物资");
        final String[] strings = new String[goodslist.size()];
        for (int i = 0; i < goodslist.size(); i++) {
            strings[i] = goodslist.get(i).getId().toString() + "-" + goodslist.get(i).getName();
        }
        builder.setSingleChoiceItems(strings, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                goodsname.setText(strings[which].toString());
            }
        });
        AlertDialog dialog = builder.create();  //创建AlertDialog对象
        dialog.show();                           //显示对话框
    }


    public void addIn() {
        Instorage instorage = new Instorage();
        instorage.setCompany(company.getText().toString());
        instorage.setDepartment(dept.getText().toString());
        instorage.setPhone(phone.getText().toString());
        instorage.setType(Integer.parseInt(type.getText().toString().split("-")[0]));
        instorage.setLinkman(linkman.getText().toString());
        instorage.setGoodsids(goodsname.getText().toString().split("-")[0]);
        instorage.setAmount(amount.getText().toString());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String s = HttpUtil.postRequestJSON("/in/addInstorageInfo", JsonUtils.object2String(instorage));
                    HashMap hashMap = JsonUtils.string2Object(s, HashMap.class);
                    Message message = new Message();
                    if (hashMap.get("msg").toString().equals("success")) {
                        message.what = 2;
                    } else {
                        message.what = 3;
                    }
                    myhandler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}