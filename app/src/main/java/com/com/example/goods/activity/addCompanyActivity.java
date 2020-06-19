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
import com.com.example.goods.entity.Company;
import com.com.example.goods.utils.HttpUtil;
import com.com.example.goods.utils.JsonUtils;

import java.io.IOException;

public class addCompanyActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back;
    private TextView company_id;
    private EditText company_name;
    private EditText company_shortname;
    private EditText company_principal;
    private EditText company_phone;
    private EditText company_address;
    private EditText company_remark;
    private Button del;
    private Button hold;
    private Company company;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company);
        initData();
        initView();
        initEvent();
    }

    @Override
    public void init() {

    }

    @Override
    public void initView() {
        back = findViewById(R.id.back);
        hold = findViewById(R.id.hold);
        company_id = findViewById(R.id.company_id);
        company_name = findViewById(R.id.company_name);
        company_shortname = findViewById(R.id.company_shortname);
        company_principal = findViewById(R.id.principal);
        company_phone = findViewById(R.id.phone);
        company_address = findViewById(R.id.address);
        company_remark = findViewById(R.id.company_remark);
    }

    @Override
    public void initData() {
        company = new Company();
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
                Intent intent = new Intent(addCompanyActivity.this, CompanyManageActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.hold:
                company.setName(company_name.getText().toString());
                company.setShortname(company_shortname.getText().toString());
                company.setPrincipal(company_principal.getText().toString());
                company.setPhone(company_phone.getText().toString());
                company.setAddress(company_address.getText().toString());
                company.setRemark(company_remark.getText().toString());
                addCompany();
                break;
        }
    }

    /**
     * 新增公司
     */
    public void addCompany() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String s = HttpUtil.postRequest("/company/add/" + JsonUtils.object2String(company));
                    Message message = Message.obtain();
                    if ("true".equals(s)) {
                        message.what = 1;
                    } else {
                        message.what = 2;
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
                Toast.makeText(addCompanyActivity.this, "新增成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(addCompanyActivity.this, "新增失败", Toast.LENGTH_SHORT).show();
            }
        }
    };
}