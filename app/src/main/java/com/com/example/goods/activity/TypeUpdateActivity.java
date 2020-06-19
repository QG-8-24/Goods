package com.com.example.goods.activity;


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

import androidx.annotation.NonNull;

import com.com.example.goods.BaseActivity;
import com.com.example.goods.R;
import com.com.example.goods.entity.Type;
import com.com.example.goods.utils.HttpUtil;
import com.com.example.goods.utils.JsonUtils;

import java.io.IOException;


public class TypeUpdateActivity extends BaseActivity implements View.OnClickListener {
    private Type type;
    private ImageView back;
    private TextView typeid;
    private EditText typename;
    private Button hold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_update);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void init() {

    }

    @Override
    public void initView() {
        back = findViewById(R.id.back);
        typeid = findViewById(R.id.typeid);
        typename = findViewById(R.id.typename);
        hold = findViewById(R.id.hold);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        type = JsonUtils.string2Object(intent.getStringExtra("msg"), Type.class);
        typename.setText(type.getName());
        typeid.setText(type.getId().toString());
    }

    @Override
    public void initEvent() {
        back.setOnClickListener(this);
        hold.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                Intent intent=new Intent(TypeUpdateActivity.this,TypeActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.hold:
                type.setName(typename.getText().toString());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            HttpUtil.postRequest("/type/updTypes/" + JsonUtils.object2String(type));
                            Message message = Message.obtain();
                            message.what = 1;
                            myhandler.sendMessage(message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
        }
    }

    //ui handler
    Handler myhandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 1) {
                Toast.makeText(TypeUpdateActivity.this, "SUCCESS", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(TypeUpdateActivity.this, "失败", Toast.LENGTH_SHORT).show();
            }
        }
    };
}