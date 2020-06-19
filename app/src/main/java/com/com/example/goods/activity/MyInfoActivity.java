package com.com.example.goods.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.com.example.goods.BaseActivity;
import com.com.example.goods.R;
import com.com.example.goods.entity.Company;
import com.com.example.goods.entity.Department;
import com.com.example.goods.entity.User;
import com.com.example.goods.utils.HttpUtil;
import com.com.example.goods.utils.JsonUtils;

import java.io.IOException;

public class MyInfoActivity extends BaseActivity implements View.OnClickListener {
    private User user;
    private Department departmentmsg;
    private Company companymsg;
    private EditText name;
    private TextView phone;
    private ImageView back;
    private RadioGroup radioGroup;
    private RadioButton nan;
    private RadioButton nv;
    private TextView department;
    private TextView company;
    private TextView remark;
    private Button hold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        initData();
        initView();
        initEvent();
        init();
    }

    @Override
    public void init() {
        getCompany();
    }

    @Override
    public void initView() {
        back = findViewById(R.id.back);
        name = findViewById(R.id.usrname);
        name.setText(user.getName());
        phone = findViewById(R.id.usrphone);
        phone.setText(user.getPhone());
        radioGroup = findViewById(R.id.rg1);
        nan = findViewById(R.id.nan);
        nv = findViewById(R.id.nv);
        if (user.getGender() == null || "男".equals(user.getGender())) {
            user.setGender("男");
            nan.setChecked(true);
        } else {
            nv.setChecked(true);
        }
        department = findViewById(R.id.departmentname);
        company = findViewById(R.id.companyname);
        remark = findViewById(R.id.remark);
        remark.setText(user.getRemark() == null ? "暂无" : user.getRemark());
        hold = findViewById(R.id.hold);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        String usr = intent.getStringExtra("usr");
        user = JsonUtils.string2Object(usr, User.class);
    }

    @Override
    public void initEvent() {
        back.setOnClickListener(this);
        hold.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = findViewById(checkedId);
                user.setGender(rb.getText().toString());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回
            case R.id.back:
                Intent intent = new Intent(MyInfoActivity.this, MyActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.hold:
                user.setName(name.getText().toString());
                final String string = JsonUtils.object2String(user);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            HttpUtil.postRequest("/user/update/" + string);
                            System.out.println("/user/update/" + string);
                            Message message = Message.obtain();
                            message.what = 2;
                            myhandler.sendMessage(message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                break;
        }
    }

    //获取用户部门信息
    public void getCompany() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String s = HttpUtil.postRequest("/user/queryDepts/" + user.getPhone());
                    departmentmsg = JsonUtils.string2Object(s, Department.class);
                    Integer companyid = departmentmsg.getCompanyid();
                    String s1 = HttpUtil.postRequest("/dept/queryDeptCompany/" + companyid);
                    companymsg = JsonUtils.string2Object(s1, Company.class);
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
                company.setText(companymsg.getName());
                department.setText(departmentmsg.getName());
            } else if (msg.what == 2) {
                Toast.makeText(MyInfoActivity.this,"成功!!!",Toast.LENGTH_SHORT).show();
            }
        }
    };
}