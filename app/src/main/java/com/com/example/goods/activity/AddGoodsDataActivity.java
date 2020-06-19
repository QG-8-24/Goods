package com.com.example.goods.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.com.example.goods.BaseActivity;
import com.com.example.goods.R;
import com.com.example.goods.entity.Goods;
import com.com.example.goods.entity.Type;
import com.com.example.goods.utils.HttpUtil;
import com.com.example.goods.utils.JsonUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class AddGoodsDataActivity extends BaseActivity implements View.OnClickListener {
    private ArrayList<Type> typeArrayList;
    private RelativeLayout rl_type;
    private Goods goods = new Goods();
    private ImageView back;
    private TextView type;
    private EditText goods_name;
    private EditText goods_unit;
    private EditText goods_specifications;
    private EditText goods_producer;
    private EditText goods_remark;
    private Button hold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goods_data);
        initView();
        initEvent();
    }

    @Override
    public void init() {

    }

    @Override
    public void initView() {
        back = findViewById(R.id.back);
        rl_type = findViewById(R.id.RL_type);
        type = findViewById(R.id.goods_typeid);
        goods_name = findViewById(R.id.goods_name);
        goods_unit = findViewById(R.id.goods_unit);
        goods_specifications = findViewById(R.id.goods_spec);
        goods_producer = findViewById(R.id.goods_producer);
        goods_remark = findViewById(R.id.goods_remark);
        hold = findViewById(R.id.hold);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        back.setOnClickListener(this);
        rl_type.setOnClickListener(this);
        hold.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                Intent intent = new Intent(AddGoodsDataActivity.this, GoodsDataActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.RL_type:
                initTypeList();
                break;
            case R.id.hold:
                addGoods();
                break;
        }
    }


    //UIHandler
    private Handler myhandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 1) {
                singleChoiceDialog();
            } else if (msg.what == 2) {
                Toast.makeText(AddGoodsDataActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(AddGoodsDataActivity.this, GoodsDataActivity.class);
                startActivity(intent2);
                finish();
            }
        }
    };

    /**
     * 单选对话框
     */
    public void singleChoiceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddGoodsDataActivity.this);
        builder.setTitle("请选择物资类别");
        final String[] strings = new String[typeArrayList.size()];
        for (int i = 0; i < typeArrayList.size(); i++) {
            strings[i] = typeArrayList.get(i).toString();
        }
        builder.setSingleChoiceItems(strings, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                type.setText(strings[which].toString());
            }
        });
        AlertDialog dialog = builder.create();  //创建AlertDialog对象
        dialog.show();                           //显示对话框
    }

    public void initTypeList() {
        final ArrayList<Type> arrayList = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String request = HttpUtil.postRequest("/type/getTypes");
                    HashMap hashMap = JsonUtils.string2Object(request, HashMap.class);
                    ArrayList types = JsonUtils.string2Object(hashMap.get("types").toString(), ArrayList.class);
                    for (Object type : types) {
                        arrayList.add(JsonUtils.string2Object(type.toString(), Type.class));

                    }
                    typeArrayList = arrayList;
                    Message message = Message.obtain();
                    message.what = 1;
                    myhandler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public void addGoods() {
        try {
            goods.setName(goods_name.getText().toString());
            goods.setTypeid(Integer.parseInt(type.getText().toString().split("-")[0]));
            goods.setUnit(goods_unit.getText().toString());
            goods.setSpecifications(goods_specifications.getText().toString());
            goods.setProducer(goods_producer.getText().toString());
            goods.setRemark(goods_remark.getText().toString());
            System.out.println(goods.getName() + "naem");
            if (goods.getName().length() == 0) {
                Toast.makeText(AddGoodsDataActivity.this, "名称不为空", Toast.LENGTH_SHORT).show();
            } else if (goods.getSpecifications().length() == 0) {
                Toast.makeText(AddGoodsDataActivity.this, "规格不为空", Toast.LENGTH_SHORT).show();
            } else if (goods.getUnit().length() == 0) {
                Toast.makeText(AddGoodsDataActivity.this, "单位不为空", Toast.LENGTH_SHORT).show();
            } else if (goods.getProducer().length() == 0) {
                Toast.makeText(AddGoodsDataActivity.this, "生产厂家不为空", Toast.LENGTH_SHORT).show();
            } else {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            HttpUtil.postRequestJSON("/goods/addGoods", JsonUtils.object2String(goods));
                            Message message = Message.obtain();
                            message.what = 2;
                            myhandler.sendMessage(message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        } catch (Exception e) {
            Toast.makeText(AddGoodsDataActivity.this, "请选择物资类别", Toast.LENGTH_SHORT).show();
        }

    }
}