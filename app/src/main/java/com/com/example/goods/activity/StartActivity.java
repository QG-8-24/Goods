package com.com.example.goods.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.com.example.goods.BaseActivity;
import com.com.example.goods.R;

import java.util.Timer;
import java.util.TimerTask;

public class StartActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        Timer timer = new Timer();
        TimerTask tast = new TimerTask() {
            @Override
            public void run() {
                SharedPreferences preferences = getSharedPreferences("phone", MODE_PRIVATE);
                String phone = preferences.getString("id", "");
                if (phone == "") {
                    Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                    startActivity(intent);//执行
                } else {
                    Intent intent2 = new Intent(StartActivity.this, HomeActivity.class);
                    startActivity(intent2);//执行
                }
                finish();
            }
        };
        timer.schedule(tast, 1000);//1秒后跳转
    }

    @Override
    public void init() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }
}
