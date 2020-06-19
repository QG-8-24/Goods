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
import com.com.example.goods.entity.Company;
import com.com.example.goods.utils.HttpUtil;
import com.com.example.goods.utils.JsonUtils;
import com.xuexiang.xui.widget.toast.XToast;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CompanyUpdateActivity extends BaseActivity implements View.OnClickListener {
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
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_update);
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
        del = findViewById(R.id.del);
        hold = findViewById(R.id.hold);
        company_id = findViewById(R.id.company_id);
        company_id.setText(company.getId().toString());
        company_name = findViewById(R.id.company_name);
        company_name.setText(company.getName());
        company_shortname = findViewById(R.id.company_shortname);
        company_shortname.setText(company.getShortname());
        company_principal = findViewById(R.id.principal);
        company_principal.setText(company.getPrincipal());
        company_phone = findViewById(R.id.phone);
        company_phone.setText(company.getPhone());
        company_address = findViewById(R.id.address);
        company_address.setText(company.getAddress());
        company_remark = findViewById(R.id.company_remark);
        company_remark.setText(company.getRemark());
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        company = JsonUtils.string2Object(intent.getStringExtra("msg"), Company.class);
    }

    @Override
    public void initEvent() {
        back.setOnClickListener(this);
        hold.setOnClickListener(this);
        del.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                Intent intent = new Intent(CompanyUpdateActivity.this, CompanyManageActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.del:
                delCompany();
                Intent intent2 = new Intent(CompanyUpdateActivity.this, HomeActivity.class);
                startActivity(intent2);
                finish();
                break;
            case R.id.hold:
                company.setName(company_name.getText().toString());
                company.setShortname(company_shortname.getText().toString());
                company.setPrincipal(company_principal.getText().toString());
                company.setPhone(company_phone.getText().toString());
                company.setAddress(company_address.getText().toString());
                company.setRemark(company_remark.getText().toString());
                updCompany();
                break;
        }
    }


    public void delCompany() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String s = HttpUtil.postRequest("/company/deleteCompany/" + company.getId());
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

    public void updCompany() {
        String phone = company.getPhone();
        String regex = "^1(3|4|5|6|7|8|9)\\d{9}$";
        if (phone.length() != 11) {
            XToast.warning(this, "手机号应为11位数").show();
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            if (isMatch) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String s = HttpUtil.postRequest("/company/update/" + JsonUtils.object2String(company));
                            Message message = Message.obtain();
                            if ("true".equals(s)) {
                                message.what = 3;
                            } else {
                                message.what = 4;
                            }
                            myhandler.sendMessage(message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            } else {
                XToast.warning(this, "您的手机号" + phone + "是错误格式！！！").show();
            }
        }

    }

    //UIHandler
    private Handler myhandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 1) {
                Toast.makeText(CompanyUpdateActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 2) {
                Toast.makeText(CompanyUpdateActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 3) {
                Toast.makeText(CompanyUpdateActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 4) {
                Toast.makeText(CompanyUpdateActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
            }
        }
    };
}