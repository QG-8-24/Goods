package com.com.example.goods.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.com.example.goods.BaseActivity;
import com.com.example.goods.R;

import com.com.example.goods.utils.MyCountDownTimer;
import com.xuexiang.xui.widget.edittext.verify.VerifyCodeEditText;
import com.xuexiang.xui.widget.toast.XToast;

public class CheckActivity extends BaseActivity {
    private VerifyCodeEditText etcode;//验证码
    private String phone;//手机号
    private TextView tvphone;
    private Button recode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        initView();
        initEvent();
        initData();
    }

    @Override
    public void init() {

    }

    @Override
    public void initView() {
        etcode = findViewById(R.id.code);
        tvphone = findViewById(R.id.tvphone);
        recode = findViewById(R.id.recode);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");//获取loginActivity创过来的手机号
        tvphone.setText(phone);
    }

    @Override
    public void initEvent() {
        /**
         * 验证码事件监听
         */
        etcode.setOnInputListener(new VerifyCodeEditText.OnInputListener() {
            @Override
            public void onComplete(String input) {
                if (input.equals("242424")) {
                    Intent intent = new Intent(CheckActivity.this, HomeActivity.class);
                    startActivity(intent);
                    //持久化手机号  再次打开APP时不用登陆
                    putPhone();
                    LoginActivity.myfinish();
                    finish();
                } else {
                    XToast.warning(CheckActivity.this, "验证码错误").show();
                }
            }

            @Override
            public void onChange(String input) {

            }

            @Override
            public void onClear() {

            }
        });
        /**
         * 倒计时
         */
        //new倒计时对象,总共的时间,每隔多少秒更新一次时间
        final MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000, 1000, recode);
        //设置Button点击事件触发倒计时
        recode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myCountDownTimer.start();
            }
        });
    }

    /**
     * 记录手机号
     */
    public void putPhone() {
        SharedPreferences.Editor editor = getSharedPreferences("phone", MODE_PRIVATE).edit();
        editor.putString("id", this.phone);
        if (editor.commit()) {
            System.out.println("写入成功");
        }
    }
}
