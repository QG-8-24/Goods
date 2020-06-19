package com.com.example.goods.activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.tv.TvContract;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ThemedSpinnerAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.com.example.goods.BaseActivity;
import com.com.example.goods.R;
import com.com.example.goods.adapter.UserManageAdapter;
import com.com.example.goods.entity.Company;
import com.com.example.goods.entity.Department;
import com.com.example.goods.entity.Instorage;
import com.com.example.goods.entity.User;
import com.com.example.goods.utils.HttpUtil;
import com.com.example.goods.utils.JsonUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class UserUpdateActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back;
    private User usermsg;
    private EditText name;
    private EditText remark;
    private RadioGroup radioGroup;
    private RadioButton nan;
    private RadioButton nv;
    private RelativeLayout department;
    private RelativeLayout company;
    private RelativeLayout role;
    private HashMap<Integer, Company> chm;
    private HashMap<Integer, Department> dhm;
    private TextView department_tv;
    private TextView company_tv;
    private TextView usrphone;
    private TextView role_tv;
    private Button hold;
    private static HashMap<Integer, String> roles = new HashMap<>();

    static {
        roles.put(0, "超级管理员");
        roles.put(1, "物资入库");
        roles.put(2, "物资出库");
        roles.put(3, "物资库存");
        roles.put(4, "物资资料");
        roles.put(5, "物资类别");
        roles.put(6, "单位/组织");
        roles.put(7, "游客");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update);
        init();
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
        radioGroup = findViewById(R.id.rg1);
        name = findViewById(R.id.usrname);
        name.setText(usermsg.getName());
        remark = findViewById(R.id.remark);
        if (usermsg.getRemark() == null) {
            remark.setText("无");
        } else {

            remark.setText(usermsg.getRemark());
        }
        usrphone = findViewById(R.id.usrphone);
        usrphone.setText(usermsg.getPhone());
        nan = findViewById(R.id.nan);
        nv = findViewById(R.id.nv);
        company = findViewById(R.id.RL_company);
        department = findViewById(R.id.RL_departmentname);
        if (usermsg.getGender() == null || "男".equals(usermsg.getGender())) {
            usermsg.setGender("男");
            nan.setChecked(true);
        } else {
            nv.setChecked(true);
        }
        company_tv = findViewById(R.id.RL_company_tv);
        department_tv = findViewById(R.id.RL_departmentname_tv);
        Company company = chm.get(dhm.get(usermsg.getDepartmentid()).getCompanyid());
        company_tv.setText(company.getId().toString() + "-" + company.getName());
        department_tv.setText(dhm.get(usermsg.getDepartmentid()).getId().toString() + "-" + dhm.get(usermsg.getDepartmentid()).getName());
        role = findViewById(R.id.RL_role);
        role_tv = findViewById(R.id.role);
        role_tv.setText(usermsg.getRoleid()+"-"+roles.get(usermsg.getRoleid()));
        hold = findViewById(R.id.hold);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        usermsg = JsonUtils.string2Object(intent.getStringExtra("msg"), User.class);
        chm = (HashMap<Integer, Company>) intent.getSerializableExtra("chm");
        dhm = (HashMap<Integer, Department>) intent.getSerializableExtra("dhm");
    }

    @Override
    public void initEvent() {
        back.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = findViewById(checkedId);
                usermsg.setGender(rb.getText().toString());
            }
        });
        company.setOnClickListener(this);
        department.setOnClickListener(this);
        role.setOnClickListener(this);
        hold.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                Intent intent = new Intent(UserUpdateActivity.this, UserManageActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.RL_company:
                singleChoiceDialog_Company();
                break;
            case R.id.RL_departmentname:
                singleChoiceDialog_DepartMent();
                break;
            case R.id.RL_role:
                singleChoiceDialog_Role();
                break;
            case R.id.hold:
                holdMsg();
                break;
        }
    }

    /**
     * 单选对话框
     */
    public void singleChoiceDialog_Company() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UserUpdateActivity.this);
        final ArrayList<String> list = new ArrayList<>();
        builder.setTitle("请选择单位");
        for (Company value : chm.values()) {
            String s = value.getId().toString() + "-" + value.getName();
            list.add(s);
        }
        final String[] strings = new String[list.size()];
        list.toArray(strings);
        builder.setSingleChoiceItems(strings, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                company_tv.setText(strings[which].toString());
                department_tv.setText("");
            }
        });
        AlertDialog dialog = builder.create();  //创建AlertDialog对象
        dialog.show();                           //显示对话框
    }

    /**
     * 单选对话框
     */
    public void singleChoiceDialog_DepartMent() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UserUpdateActivity.this);
        final ArrayList<String> list = new ArrayList<>();
        builder.setTitle("请选择部门");
        String[] split = company_tv.getText().toString().split("-");
        for (Department value : dhm.values()) {
            System.out.println(value);
            if (split[0].equals(value.getCompanyid().toString())) {
                String s = value.getId() + "-" + value.getName();
                list.add(s);
            }
        }
        final String[] strings = new String[list.size()];
        list.toArray(strings);
        builder.setSingleChoiceItems(strings, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                department_tv.setText(strings[which].toString());
            }
        });
        AlertDialog dialog = builder.create();  //创建AlertDialog对象
        dialog.show();                           //显示对话框
    }

    /**
     * 单选对话框
     */
    public void singleChoiceDialog_Role() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UserUpdateActivity.this);
        builder.setTitle("请选择权限");
        final String[] strings = new String[]{"1-物资入库", "2-物资出库", "3-物资库存", "4-物资资料", "5-物资类别", "6-单位/组织", "7-游客"};
        builder.setSingleChoiceItems(strings, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                role_tv.setText(strings[which].toString());
            }
        });
        AlertDialog dialog = builder.create();  //创建AlertDialog对象
        dialog.show();                           //显示对话框
    }

    public void holdMsg() {
        usermsg.setName(name.getText().toString());
        usermsg.setDepartmentid(Integer.parseInt(department_tv.getText().toString().split("-")[0]));
        usermsg.setRemark(remark.getText().toString());
        usermsg.setRoleid(Integer.parseInt(role_tv.getText().toString().split("-")[0]));
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpUtil.postRequest("/user/update/"+JsonUtils.object2String(usermsg));
                    Message message=Message.obtain();
                    message.what=1;
                    myhandler.sendMessage(message);
                } catch (IOException e) {
                    Message message=Message.obtain();
                    message.what=2;
                    myhandler.sendMessage(message);
                }
            }
        }).start();
    }

    //UIHandler
    private Handler myhandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 1) {
                Toast.makeText(UserUpdateActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(UserUpdateActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
            }
        }
    };
}