package com.com.example.goods.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.com.example.goods.BaseActivity;
import com.com.example.goods.R;
import com.com.example.goods.entity.User;
import com.com.example.goods.utils.HttpUtil;
import com.com.example.goods.utils.JsonUtils;

import java.io.IOException;


public class HomeActivity extends BaseActivity implements View.OnClickListener {
    private User user;
    private String phone;
    private LinearLayout home;
    private LinearLayout my;
    private LinearLayout company;
    private LinearLayout type;
    private LinearLayout goodsdata;
    private LinearLayout inventory;
    private LinearLayout in;
    private LinearLayout out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initData();
        initView();
        initEvent();

    }

    @Override
    public void init() {

    }

    @Override
    public void initView() {
        home = findViewById(R.id.llhome);
        my = findViewById(R.id.llmy);
        company = findViewById(R.id.company);
        type = findViewById(R.id.ll_type);
        goodsdata = findViewById(R.id.goodsdata);
        inventory = findViewById(R.id.inventory);
        in = findViewById(R.id.ll_in);
        out = findViewById(R.id.ll_out);

    }

    @Override
    public void initData() {
        getUsr();
    }

    @Override
    public void initEvent() {
        home.setOnClickListener(this);
        my.setOnClickListener(this);
        company.setOnClickListener(this);
        type.setOnClickListener(this);
        goodsdata.setOnClickListener(this);
        inventory.setOnClickListener(this);
        in.setOnClickListener(this);
        out.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llhome:
                Intent intent_home = new Intent(HomeActivity.this, HomeActivity.class);
                startActivity(intent_home);
                finish();
                break;
            case R.id.llmy:
                Intent intent_my = new Intent(HomeActivity.this, MyActivity.class);
                startActivity(intent_my);
                finish();
                break;
            case R.id.ll_in:
                if (user.getRoleid() == 1 || user.getRoleid() == 0) {
                    Intent intent1 = new Intent(HomeActivity.this, InActivity.class);
                    startActivity(intent1);
                } else {
                    Toast.makeText(HomeActivity.this, "没有权限", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ll_out:
                if (user.getRoleid() == 2 || user.getRoleid() == 0) {
                    Intent intent2 = new Intent(HomeActivity.this, OutActivity.class);
                    startActivity(intent2);
                } else {
                    Toast.makeText(HomeActivity.this, "没有权限", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.inventory:
                if (user.getRoleid() == 3 || user.getRoleid() == 0) {
                    Intent intent3 = new Intent(HomeActivity.this, InventoryActivity.class);
                    startActivity(intent3);
                } else {
                    Toast.makeText(HomeActivity.this, "没有权限", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.goodsdata:
                if (user.getRoleid() == 4 || user.getRoleid() == 0) {
                    Intent intent4 = new Intent(HomeActivity.this, GoodsDataActivity.class);
                    startActivity(intent4);
                } else {
                    Toast.makeText(HomeActivity.this, "没有权限", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ll_type:
                if (user.getRoleid() == 5 || user.getRoleid() == 0) {
                    Intent intent5 = new Intent(HomeActivity.this, TypeActivity.class);
                    startActivity(intent5);
                } else {
                    Toast.makeText(HomeActivity.this, "没有权限", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.company:
                if (user.getRoleid() == 6 || user.getRoleid() == 0) {
                    Intent intent6 = new Intent(HomeActivity.this, CompanyManageActivity.class);
                    startActivity(intent6);
                } else {
                    Toast.makeText(HomeActivity.this, "没有权限", Toast.LENGTH_SHORT).show();
                }
                break;


        }
    }

    //获取用户信息
    public void getUsr() {
        SharedPreferences preferences = getSharedPreferences("phone", MODE_PRIVATE);
        final String phone = preferences.getString("id", "");

        //获取用户信息
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String s = HttpUtil.postRequest("/login/" + phone);
                    user = JsonUtils.string2Object(s, User.class);
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
                SharedPreferences.Editor editor = getSharedPreferences("usr", MODE_PRIVATE).edit();
                editor.putString("id", JsonUtils.object2String(user));
                if (editor.commit()) {
                    System.out.println("usr写入成功");
                }
            }
        }
    };
}
