package com.com.example.goods.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.com.example.goods.BaseActivity;
import com.com.example.goods.R;
import com.com.example.goods.adapter.CompanyManageAdapter;
import com.com.example.goods.adapter.DepartmentManageAdapter;
import com.com.example.goods.adapter.UserManageAdapter;
import com.com.example.goods.entity.Company;
import com.com.example.goods.entity.Department;
import com.com.example.goods.entity.User;
import com.com.example.goods.utils.HttpUtil;
import com.com.example.goods.utils.JsonUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CompanyManageActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private EditText ed_search;
    private HashMap<Integer, Company> chm;
    private HashMap<Integer, Department> dhm;
    private ImageView back;
    private ListView companylist;
    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_manage);
        initView();
        init();
        initEvent();
        initData();
    }

    @Override
    public void init() {

    }

    @Override
    public void initView() {
        companylist = findViewById(R.id.companylist);
        add = findViewById(R.id.add);
        back = findViewById(R.id.back);
        ed_search = findViewById(R.id.ED_search);
    }

    @Override
    public void initData() {
        initList();
    }

    @Override
    public void initEvent() {
        companylist.setOnItemClickListener(this);
        add.setOnClickListener(this);
        back.setOnClickListener(this);
        changeListener();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CompanyManageAdapter adapter = (CompanyManageAdapter) companylist.getAdapter();
        final Company company = adapter.getData().get(position);
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("单位操作").setIcon(R.drawable.department1)
                .setMessage(company.getName())
                .setPositiveButton("部门管理", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(CompanyManageActivity.this, DepartmentManageActivity.class);
                        intent.putExtra("msg", JsonUtils.object2String(company));  // 传递参数，根据需要填写
                        startActivity(intent);
                        finish();
                    }
                })
                //进入修改页面  将user传过去
                .setNeutralButton("修改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(CompanyManageActivity.this, CompanyUpdateActivity.class);
                        intent.putExtra("msg", JsonUtils.object2String(company));  // 传递参数，根据需要填写
                        startActivity(intent);
                        finish();
                    }
                })
                .create();
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                Intent intent = new Intent(CompanyManageActivity.this, addCompanyActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    public void initList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String s1 = HttpUtil.postRequest("/company/all");
                    List list = JsonUtils.string2Object(s1, List.class);
                    chm = new HashMap<>();
                    dhm = new HashMap<>();
                    for (Object o : list) {
                        Company company = JsonUtils.string2Object(o.toString(), Company.class);
                        chm.put(company.getId(), company);
                        String s2 = HttpUtil.postRequest("/company/queryCompanyDepts/" + company.getId().toString());
                        List list1 = JsonUtils.string2Object(s2, List.class);
                        for (Object o1 : list1) {
                            Department department = JsonUtils.string2Object(o1.toString(), Department.class);
                            dhm.put(department.getId(), department);
                        }
                    }
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
                List<Company> list = new ArrayList<>();
                for (Company value : chm.values()) {
                    list.add(value);
                }
                CompanyManageAdapter companyManageAdapter = new CompanyManageAdapter(CompanyManageActivity.this, list);
                companylist.setAdapter(companyManageAdapter);
            } else if (msg.what == 2) {
                initList();
            }
        }
    };

    //search 监听
    public void changeListener() {
        ed_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                CompanyManageAdapter companyManageAdapter = (CompanyManageAdapter) companylist.getAdapter();
                List<Company> data = new ArrayList<>();
                for (Company value : chm.values()) {
                    data.add(value);
                }
                List<Company> newdata = new ArrayList<>();
                for (Company datum : data) {
                    if (datum.toString().contains(s)) {
                        newdata.add(datum);
                    }
                }
                companyManageAdapter.setData(newdata);
                companylist.setAdapter(companyManageAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}