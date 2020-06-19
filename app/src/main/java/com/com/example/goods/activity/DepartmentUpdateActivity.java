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
import android.widget.TextView;
import android.widget.Toast;

import com.com.example.goods.BaseActivity;
import com.com.example.goods.R;
import com.com.example.goods.entity.Department;
import com.com.example.goods.utils.HttpUtil;
import com.com.example.goods.utils.JsonUtils;

import java.io.IOException;

public class DepartmentUpdateActivity extends BaseActivity implements View.OnClickListener {
    private Department department;
    private ImageView back;
    private TextView id;
    private EditText name;
    private EditText remark;
    private Button hold;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_update);
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
        id = findViewById(R.id.de_id);
        id.setText(department.getId().toString());
        name = findViewById(R.id.de_name);
        name.setText(department.getName());
        remark = findViewById(R.id.de_remark);
        remark.setText(department.getRemark());
        hold = findViewById(R.id.hold);
    }

    @Override
    public void initData() {
        department = JsonUtils.string2Object(getIntent().getStringExtra("msg"), Department.class);
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
                Intent intent = new Intent(DepartmentUpdateActivity.this, CompanyManageActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.hold:
                upDepartment();
                break;
        }
    }

    public void upDepartment() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    department.setName(name.getText().toString());
                    department.setRemark(remark.getText().toString());
                    String s = HttpUtil.postRequest("/dept/upDept/" + JsonUtils.object2String(department));
                    Message message = Message.obtain();
                    if ("true".equals(s)) {
                        message.what = 1;
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
                Toast.makeText(DepartmentUpdateActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DepartmentUpdateActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
            }
        }
    };
}