package com.com.example.goods.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.com.example.goods.BaseActivity;
import com.com.example.goods.R;
import com.com.example.goods.adapter.TypeAdapter;
import com.com.example.goods.entity.Type;
import com.com.example.goods.utils.HttpUtil;
import com.com.example.goods.utils.JsonUtils;

import java.io.IOException;
import java.net.URLEncoder;

public class AddTypeActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back;
    private EditText typename;
    private Button hold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_type);
        initView();
        initEvent();
    }

    @Override
    public void init() {

    }

    @Override
    public void initView() {
        back = findViewById(R.id.back);
        typename = findViewById(R.id.typename);
        hold = findViewById(R.id.hold);
    }

    @Override
    public void initData() {

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
                Intent intent=new Intent(AddTypeActivity.this,TypeActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.hold:
                final String name = typename.getText().toString();
                if (name.equals("") || name.length() == 0) {
                    Toast.makeText(AddTypeActivity.this, "类别名称不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                HttpUtil.postRequest("/type/addTypes/" + name);
                                Message message = Message.obtain();
                                message.what = 1;
                                myhandler.sendMessage(message);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
                break;
        }
    }

    //UIHandler
    private Handler myhandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 1) {
                Toast.makeText(AddTypeActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AddTypeActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
            }
        }
    };
}