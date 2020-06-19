
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
import android.widget.BaseAdapter;
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

public class GoodsDataUpdateActivity extends BaseActivity implements View.OnClickListener {
    private ArrayList<Type> typeArrayList;
    private HashMap<Integer, Type> hashMap_type;
    private RelativeLayout rl_type;
    private Goods goods;
    private ImageView back;
    private TextView type;
    private TextView goods_id;
    private EditText goods_name;
    private EditText goods_unit;
    private EditText goods_specifications;
    private EditText goods_producer;
    private EditText goods_remark;
    private Button del;
    private Button hold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_data_updata);
        initData();
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
        goods_id = findViewById(R.id.goods_id);
        goods_id.setText(goods.getId().toString());
        goods_name = findViewById(R.id.goods_name);
        goods_name.setText(goods.getName());
        goods_unit = findViewById(R.id.goods_unit);
        goods_unit.setText(goods.getUnit());
        goods_specifications = findViewById(R.id.goods_spec);
        goods_specifications.setText(goods.getSpecifications());
        goods_producer = findViewById(R.id.goods_producer);
        goods_producer.setText(goods.getProducer());
        goods_remark = findViewById(R.id.goods_remark);
        goods_remark.setText(goods.getRemark());
        inithashMap();
        del = findViewById(R.id.del);
        hold = findViewById(R.id.hold);
    }

    @Override
    public void initData() {
        goods = JsonUtils.string2Object(getIntent().getStringExtra("msg"), Goods.class);
    }

    @Override
    public void initEvent() {
        back.setOnClickListener(this);
        rl_type.setOnClickListener(this);
        del.setOnClickListener(this);
        hold.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                Intent intent = new Intent(GoodsDataUpdateActivity.this, GoodsDataActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.RL_type:
                initTypeList();
                break;
            case R.id.del:
                delGoods();
                Intent intent2 = new Intent(GoodsDataUpdateActivity.this, HomeActivity.class);
                startActivity(intent2);
                Toast.makeText(GoodsDataUpdateActivity.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.hold:
                upGoods();
                Intent intent3 = new Intent(GoodsDataUpdateActivity.this, HomeActivity.class);
                startActivity(intent3);
                Toast.makeText(GoodsDataUpdateActivity.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                Toast.makeText(GoodsDataUpdateActivity.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }

    /**
     * 单选对话框
     */
    public void singleChoiceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(GoodsDataUpdateActivity.this);
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

    //UIHandler
    private Handler myhandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 1) {
                singleChoiceDialog();
            } else if (msg.what == 2) {
                type.setText(goods.getTypeid().toString() + "-" + hashMap_type.get(goods.getTypeid()).getName());
            }
        }
    };

    public void inithashMap() {
        final ArrayList<Type> arrayList = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String request = HttpUtil.postRequest("/type/getTypes");
                    HashMap hashMap = JsonUtils.string2Object(request, HashMap.class);
                    ArrayList types = JsonUtils.string2Object(hashMap.get("types").toString(), ArrayList.class);
                    hashMap_type = new HashMap<>();
                    for (Object type : types) {
                        Type type1 = JsonUtils.string2Object(type.toString(), Type.class);
                        hashMap_type.put(type1.getId(), type1);
                    }
                    Message message = Message.obtain();
                    message.what = 2;
                    myhandler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void upGoods() {
        goods.setName(goods_name.getText().toString());
        goods.setTypeid(Integer.parseInt(type.getText().toString().split("-")[0]));
        goods.setUnit(goods_unit.getText().toString());
        goods.setSpecifications(goods_specifications.getText().toString());
        goods.setProducer(goods_producer.getText().toString());
        goods.setRemark(goods_remark.getText().toString());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String s = HttpUtil.postRequestJSON("/goods/updGoods", JsonUtils.object2String(goods));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void delGoods() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpUtil.postRequest("/goods/delGoods/" + goods.getId().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}