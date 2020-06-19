package com.com.example.goods.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

public class DepartmentManageActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private EditText ed_search;
    List<Department> data;
    private Company company;
    private ImageView back;
    private ListView departmentlist;
    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_manage);
        initData();
        initView();
        init();
        initEvent();
    }

    @Override
    public void init() {

    }

    @Override
    public void initView() {
        departmentlist = findViewById(R.id.departmentlist);
        add = findViewById(R.id.add);
        back = findViewById(R.id.back);
        ed_search = findViewById(R.id.ED_search);
    }

    @Override
    public void initData() {
        company = JsonUtils.string2Object(getIntent().getStringExtra("msg"), Company.class);
        getDepartments();
    }

    @Override
    public void initEvent() {
        add.setOnClickListener(this);
        back.setOnClickListener(this);
        departmentlist.setOnItemClickListener(this);
        changeListener();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                Intent intent = new Intent(DepartmentManageActivity.this, AdddePartmentActivity.class);
                intent.putExtra("msg", company.getId().toString());
                startActivity(intent);
                finish();
                break;
            case R.id.back:
                Intent intent1 = new Intent(DepartmentManageActivity.this, CompanyManageActivity.class);
                startActivity(intent1);
                finish();
                break;

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DepartmentManageAdapter adapter = (DepartmentManageAdapter) departmentlist.getAdapter();
        final Department department = adapter.getData().get(position);
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("部门操作").setIcon(R.drawable.department1)
                .setMessage(department.getName())
                .setPositiveButton("删除", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        delDepartments(department.getId());
                    }
                })
                //进入修改页面  将department传过去
                .setNeutralButton("修改", new DialogInterface.OnClickListener() {//添加普通按钮
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(DepartmentManageActivity.this, DepartmentUpdateActivity.class);
                        intent.putExtra("msg", JsonUtils.object2String(department));  // 传递参数，根据需要填写
                        startActivity(intent);
                        finish();
                    }
                })
                .create();
        alertDialog.show();
    }

    /**
     * get all department
     */
    public void getDepartments() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String s = HttpUtil.postRequest("/company/queryCompanyDepts/" + company.getId().toString());
                    data = JSON.parseArray(s, Department.class);
                    Message message = Message.obtain();
                    message.what = 1;
                    myhandler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * get all department
     */
    public void delDepartments(final int id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String s = HttpUtil.postRequest("/dept/deleteDept/" + id);
                    Message message = Message.obtain();
                    System.out.println(s + "sadsadsa  44444444");
                    if ("false".equals(s)) {
                        message.what = 3;
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
                DepartmentManageAdapter adapter = new DepartmentManageAdapter(DepartmentManageActivity.this, data);
                departmentlist.setAdapter(adapter);
            } else if (msg.what == 2) {
                getDepartments();
                Toast.makeText(DepartmentManageActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DepartmentManageActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
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
                DepartmentManageAdapter departmentManageAdapter = (DepartmentManageAdapter) departmentlist.getAdapter();
                List<Department> data1 = new ArrayList<>();
                for (Department datum : data) {
                    if (datum.toString().contains(s)) {
                        data1.add(datum);
                    }
                }
                departmentManageAdapter.setData(data1);
                departmentlist.setAdapter(departmentManageAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}