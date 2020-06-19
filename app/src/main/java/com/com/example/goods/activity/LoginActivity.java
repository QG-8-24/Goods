package com.com.example.goods.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.com.example.goods.BaseActivity;
import com.com.example.goods.R;
import com.xuexiang.xui.widget.toast.XToast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private static LoginActivity loginActivity;
    private ImageView login;
    private String phone;
    private EditText etphone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginActivity = this;
        initView();
        initEvent();
    }

    public static void myfinish() {
        loginActivity.finish();
    }

    @Override
    public void init() {

    }

    @Override
    public void initView() {
        login = findViewById(R.id.login);
        etphone = findViewById(R.id.etphone);

    }

    @Override
    public void initData() {

    }

    //设置点击事件
    @Override
    public void initEvent() {
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                sendcode();
                break;
        }
    }

    /**
     * 发送验证码伪代码
     */
    void sendcode() {
        phone = etphone.getText().toString();
        String regex = "^1(3|4|5|6|7|8|9)\\d{9}$";
        if (phone.length() != 11) {
            if (phone.equals("000000")) {
                Intent intent = new Intent(LoginActivity.this, CheckActivity.class);
                //将手机号传过去
                intent.putExtra("phone", phone);
                startActivity(intent);
            } else {
                XToast.warning(this, "手机号应为11位数").show();
            }
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            if (isMatch) {
                Intent intent = new Intent(LoginActivity.this, CheckActivity.class);
                //将手机号传过去
                intent.putExtra("phone", phone);
                startActivity(intent);
            } else {
                XToast.warning(this, "您的手机号" + phone + "是错误格式！！！").show();
            }
        }
    }
}
