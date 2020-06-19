package com.com.example.goods.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.com.example.goods.BaseActivity;
import com.com.example.goods.R;
import com.com.example.goods.entity.User;
import com.com.example.goods.utils.HttpUtil;
import com.com.example.goods.utils.JsonUtils;

import java.io.IOException;

public class MyActivity extends BaseActivity implements View.OnClickListener {
    private User user;
    private LinearLayout home;
    private LinearLayout my;
    private Button logout;
    private TextView tx_MyInfo;
    private TextView tx_AboutUs;
    private TextView tx_userManage;
    private TextView tx_companyManage;
    private TextView usrmsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        initView();
        initEvent();
        initData();
    }

    @Override
    public void init() {

    }

    @Override
    public void initView() {
        home = findViewById(R.id.llhome);
        my = findViewById(R.id.llmy);
        logout = findViewById(R.id.logout);
        tx_MyInfo = findViewById(R.id.tx_MyInfo);
        tx_AboutUs = findViewById(R.id.tx_aboutus);
        tx_userManage = findViewById(R.id.userManage);
        tx_companyManage = findViewById(R.id.companymanage);
        usrmsg = findViewById(R.id.usrmsg);
    }

    @Override
    public void initData() {
        getUsr();
    }

    @Override
    public void initEvent() {
        home.setOnClickListener(this);
        my.setOnClickListener(this);
        logout.setOnClickListener(this);
        tx_MyInfo.setOnClickListener(this);
        tx_AboutUs.setOnClickListener(this);
        tx_userManage.setOnClickListener(this);
        tx_companyManage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llhome:
                Intent intent = new Intent(MyActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.llmy:
                Intent intent2 = new Intent(MyActivity.this, MyActivity.class);
                startActivity(intent2);
                finish();
                break;
            case R.id.logout:
                SharedPreferences.Editor phone = getSharedPreferences("phone", MODE_PRIVATE).edit();
                phone.remove("id");
                phone.commit();
                SharedPreferences.Editor usr = getSharedPreferences("usr", MODE_PRIVATE).edit();
                phone.remove("id");
                usr.commit();
                Intent intent3 = new Intent(MyActivity.this, LoginActivity.class);
                startActivity(intent3);
                finish();
                break;
            //跳转个人信息修改页面
            case R.id.tx_MyInfo:
                Intent intent4 = new Intent(MyActivity.this, MyInfoActivity.class);
                intent4.putExtra("usr", JsonUtils.object2String(user));
                startActivity(intent4);
                finish();
                break;
            //跳转关于我们
            case R.id.tx_aboutus:
                Intent intent5 = new Intent(MyActivity.this, AboutUsActivity.class);
                startActivity(intent5);
                break;
            //跳转人员管理
            case R.id.userManage:
                if (user.getRoleid() == 0) {
                    Intent intent6 = new Intent(MyActivity.this, UserManageActivity.class);
                    startActivity(intent6);
                } else {
                    Toast.makeText(MyActivity.this, "没有权限", Toast.LENGTH_SHORT).show();
                }

                break;
            //跳转单位管理
            case R.id.companymanage:
                if (user.getRoleid() == 0 || user.getRoleid() == 6) {
                    Intent intent8 = new Intent(MyActivity.this, CompanyManageActivity.class);
                    startActivity(intent8);
                } else {
                    Toast.makeText(MyActivity.this, "没有权限", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //获取用户信息
    public void getUsr() {
        SharedPreferences preferences = getSharedPreferences("usr", MODE_PRIVATE);
        String usr = preferences.getString("id", "");
        user = JsonUtils.string2Object(usr, User.class);
        Message message = Message.obtain();
        message.what = 1;
        myhandler.sendMessage(message);
    }

    //UIHandler
    private Handler myhandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 1) {
                usrmsg.setText("\n\n" + user.getName() + "\n" + user.getPhone());
            }
        }
    };
}
