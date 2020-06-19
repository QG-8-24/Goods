package com.com.example.goods.activity;


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

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.com.example.goods.BaseActivity;
import com.com.example.goods.R;
import com.com.example.goods.adapter.TypeAdapter;
import com.com.example.goods.adapter.UserManageAdapter;
import com.com.example.goods.entity.Company;
import com.com.example.goods.entity.Department;
import com.com.example.goods.entity.Type;
import com.com.example.goods.entity.User;
import com.com.example.goods.utils.HttpUtil;
import com.com.example.goods.utils.JsonUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserManageActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private EditText ed_search;
    private ImageView back;
    private ListView userList;
    private List<User> list;
    private EditText ED_search;
    private HashMap<Integer, Company> chm;
    private HashMap<Integer, Department> dhm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manage);
        initView();
        init();
        initEvent();
    }

    @Override
    public void init() {
        initUserList();
    }

    @Override
    public void initView() {
        userList = findViewById(R.id.userlist);
        ED_search = findViewById(R.id.ED_search);
        back = findViewById(R.id.back);
        ed_search = findViewById(R.id.ED_search);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        userList.setOnItemClickListener(this);
        back.setOnClickListener(this);
        changeListener();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        UserManageAdapter adapter = (UserManageAdapter) userList.getAdapter();
        final User chuser = adapter.getData().get(position);
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("人员操作").setIcon(R.drawable.person)
                .setMessage(chuser.getName())
                .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final String phone = chuser.getPhone();
                        if ("000000".equals(phone)) {
                            Toast.makeText(UserManageActivity.this, "不能删除超级用户", Toast.LENGTH_SHORT).show();
                        } else {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        HttpUtil.postRequest("/user/delete/" + phone);
                                        Message message = Message.obtain();
                                        message.what = 2;
                                        myhandler.sendMessage(message);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        }
                    }
                })
                //进入修改页面  将user传过去
                .setNeutralButton("修改", new DialogInterface.OnClickListener() {//添加普通按钮
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final String phone = chuser.getPhone();
                        if ("000000".equals(phone)) {
                            Toast.makeText(UserManageActivity.this, "不能修改超级用户", Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent = new Intent(UserManageActivity.this, UserUpdateActivity.class);
                            intent.putExtra("msg", JsonUtils.object2String(chuser));  // 传递参数，根据需要填写
                            intent.putExtra("chm", (Serializable) chm);
                            intent.putExtra("dhm", (Serializable) dhm);
                            startActivity(intent);
                            finish();
                        }

                    }
                })
                .create();
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    public void initUserList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String s = HttpUtil.postRequest("/user/all");
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
                    UserManageActivity.this.list = JSON.parseArray(s, User.class);
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
                UserManageAdapter userManageAdapter = new UserManageAdapter(UserManageActivity.this, list, chm, dhm);
                userList.setAdapter(userManageAdapter);
            } else if (msg.what == 2) {
                initUserList();
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
                UserManageAdapter userManageAdapter = (UserManageAdapter) userList.getAdapter();
                List<User> data = list;
                List<User> newdata = new ArrayList<>();
                for (User datum : data) {
                    String s1 = dhm.get(datum.getDepartmentid()).toString() + chm.get(dhm.get(datum.getDepartmentid()).getCompanyid()).toString() + datum.toString();
                    if (s1.contains(s)) {
                        newdata.add(datum);
                    }
                }
                userManageAdapter.setData(newdata);
                userList.setAdapter(userManageAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}