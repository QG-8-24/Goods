package com.com.example.goods.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.com.example.goods.BaseActivity;
import com.com.example.goods.R;
import com.com.example.goods.entity.Company;
import com.com.example.goods.entity.Department;
import com.com.example.goods.utils.HttpUtil;
import com.com.example.goods.utils.JsonUtils;

import java.io.IOException;

public class AdddePartmentActivity extends BaseActivity implements View.OnClickListener {
    private Integer companyid;
    private EditText name;
    private EditText remark;
    private ImageView back;
    private Button hold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addde_partment);
        initData();
        init();
        initView();
        initEvent();
    }

    @Override
    public void init() {

    }

    @Override
    public void initView() {
        back = findViewById(R.id.back);
        name = findViewById(R.id.new_name);
        remark = findViewById(R.id.new_remark);
        hold=findViewById(R.id.hold);
    }

    @Override
    public void initData() {
        companyid = Integer.parseInt(getIntent().getStringExtra("msg"));
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
                Intent intent = new Intent(AdddePartmentActivity.this, CompanyManageActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.hold:
                addDept();
                break;
        }
    }

    public void addDept() {
        final Department department = new Department();
        department.setName(name.getText().toString());
        department.setCompanyid(companyid);
        department.setRemark(remark.getText().toString());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String s = HttpUtil.postRequest("/dept/add/" + JsonUtils.object2String(department));
                    Message message=Message.obtain();
                    if ("true".equals(s)) {
                        message.what=1;
                    }
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
                Toast.makeText(AdddePartmentActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AdddePartmentActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
            }
        }
    };
}