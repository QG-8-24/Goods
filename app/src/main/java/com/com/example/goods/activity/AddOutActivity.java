package com.com.example.goods.activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.com.example.goods.BaseActivity;
import com.com.example.goods.R;
import com.com.example.goods.entity.Company;
import com.com.example.goods.entity.Department;
import com.com.example.goods.entity.Goods;
import com.com.example.goods.entity.Outstorage;
import com.com.example.goods.entity.User;
import com.com.example.goods.utils.HttpUtil;
import com.com.example.goods.utils.JsonUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddOutActivity extends BaseActivity implements View.OnClickListener {
    private ArrayList<Goods> goodslist;
    private List users = new ArrayList<>();
    private HashMap<Integer, Company> chm;
    private HashMap<Integer, Department> dhm;
    private TextView type;
    private TextView company;
    private TextView dept;
    private TextView linkman;
    private TextView name;
    private EditText amount;
    private ImageView back;
    private Button hold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_out);
        initView();
        initEvent();
    }

    @Override
    public void init() {

    }

    @Override
    public void initView() {
        back = findViewById(R.id.back);
        type = findViewById(R.id.type);
        company = findViewById(R.id.company);
        dept = findViewById(R.id.department);
        linkman = findViewById(R.id.linkman);
        name = findViewById(R.id.name);
        amount = findViewById(R.id.amount);
        hold = findViewById(R.id.hold);
    }

    @Override
    public void initData() {
    }

    @Override
    public void initEvent() {
        back.setOnClickListener(this);
        company.setOnClickListener(this);
        dept.setOnClickListener(this);
        type.setOnClickListener(this);
        linkman.setOnClickListener(this);
        name.setOnClickListener(this);
        hold.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.company:
                initList_company();
                break;
            case R.id.department:
                init_dept();
                break;
            case R.id.type:
                singleChoiceDialog_type();
                break;
            case R.id.linkman:
                initusrlist();
                break;
            case R.id.name:
                getAllGoods();
                break;
            case R.id.hold:
                addOut();
                break;
        }
    }

    public void initList_company() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String s1 = HttpUtil.postRequest("/company/all");
                    List list = JsonUtils.string2Object(s1, List.class);
                    chm = new HashMap<>();
                    for (Object o : list) {
                        Company company = JsonUtils.string2Object(o.toString(), Company.class);
                        chm.put(company.getId(), company);
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

    public void init_dept() {
        if (company.getText().toString().equals("XXX")) {
            Toast.makeText(AddOutActivity.this, "请先选择来往单位", Toast.LENGTH_SHORT).show();
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String s1 = HttpUtil.postRequest("/company/all");
                        List list = JsonUtils.string2Object(s1, List.class);
                        dhm = new HashMap<>();
                        String s2 = HttpUtil.postRequest("/company/queryCompanyDepts/" + company.getText().toString().split("-")[0]);
                        List list1 = JsonUtils.string2Object(s2, List.class);
                        for (Object o1 : list1) {
                            Department department = JsonUtils.string2Object(o1.toString(), Department.class);
                            dhm.put(department.getId(), department);
                        }
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

    /**
     * 单选对话框
     */
    public void singleChoiceDialog_company() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddOutActivity.this);
        final ArrayList<String> list = new ArrayList<>();
        builder.setTitle("请选择来往单位");
        for (Company value : chm.values()) {
            String s = value.getId().toString() + "-" + value.getName();
            list.add(s);
        }
        final String[] strings = new String[list.size()];
        list.toArray(strings);
        builder.setSingleChoiceItems(strings, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                company.setText(strings[which].toString());
                dept.setText("XXX");
            }
        });
        AlertDialog dialog = builder.create();  //创建AlertDialog对象
        dialog.show();                           //显示对话框
    }

    /**
     * 单选对话框
     */
    public void singleChoiceDialog_dept() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddOutActivity.this);
        final ArrayList<String> list = new ArrayList<>();
        builder.setTitle("请选择来往部门");
        for (Department value : dhm.values()) {
            String s = value.getId().toString() + "-" + value.getName();
            list.add(s);
        }
        final String[] strings = new String[list.size()];
        list.toArray(strings);
        builder.setSingleChoiceItems(strings, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dept.setText(strings[which].toString());
            }
        });
        AlertDialog dialog = builder.create();  //创建AlertDialog对象
        dialog.show();                           //显示对话框
    }

    /**
     * 单选对话框
     */
    public void singleChoiceDialog_user() {
        if (dept.getText().toString().split("-")[0].equals("XXX")) {
            Toast.makeText(AddOutActivity.this, "请先选择部门", Toast.LENGTH_SHORT).show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(AddOutActivity.this);
            final ArrayList<String> list = new ArrayList<>();
            builder.setTitle("请选择联系人");
            String s1 = dept.getText().toString().split("-")[0];
            for (Object user : users) {
                User user1 = JsonUtils.string2Object(JsonUtils.object2String(user), User.class);
                if (user1.getDepartmentid().toString().equals(s1)) {
                    list.add(user1.getName() + "-" + user1.getPhone());
                }
            }
            final String[] strings = new String[list.size()];
            list.toArray(strings);
            builder.setSingleChoiceItems(strings, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    linkman.setText(strings[which].toString());
                }
            });
            AlertDialog dialog = builder.create();  //创建AlertDialog对象
            dialog.show();                           //显示对话框
        }

    }

    /**
     * 单选对话框
     */
    public void singleChoiceDialog_type() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddOutActivity.this);
        builder.setTitle("请选择发放类型");
        final String[] strings = new String[]{"1-申请发放", "2-直接发放"};
        builder.setSingleChoiceItems(strings, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                type.setText(strings[which].toString());
            }
        });
        AlertDialog dialog = builder.create();  //创建AlertDialog对象
        dialog.show();                           //显示对话框
    }

    public void initusrlist() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String s = HttpUtil.postRequest("/user/all");
                    users = JsonUtils.string2Object(s, List.class);
                    Message message = new Message();
                    message.what = 3;
                    myhandler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 单选对话框
     */
    public void singleChoiceDialog_goods() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddOutActivity.this);
        final ArrayList<String> list = new ArrayList<>();
        builder.setTitle("请选择入库物资");
        final String[] strings = new String[goodslist.size()];
        for (int i = 0; i < goodslist.size(); i++) {
            strings[i] = goodslist.get(i).getId().toString() + "-" + goodslist.get(i).getName();
        }
        builder.setSingleChoiceItems(strings, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                name.setText(strings[which].toString());
            }
        });
        AlertDialog dialog = builder.create();  //创建AlertDialog对象
        dialog.show();                           //显示对话框
    }

    public void getAllGoods() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String s = HttpUtil.postRequest("/goods/getall");
                    ArrayList<Goods> arrayList = JsonUtils.string2Object(s, ArrayList.class);
                    goodslist = new ArrayList<>();
                    for (Object o : arrayList) {
                        Goods goods = JsonUtils.string2Object(JsonUtils.object2String(o), Goods.class);
                        goodslist.add(goods);
                    }
                    Message message = Message.obtain();
                    message.what = 4;
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
                singleChoiceDialog_company();
            } else if (msg.what == 2) {
                singleChoiceDialog_dept();
            } else if (msg.what == 3) {
                singleChoiceDialog_user();
            } else if (msg.what == 4) {
                singleChoiceDialog_goods();
            } else if (msg.what == 5) {
                Toast.makeText(AddOutActivity.this, "成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddOutActivity.this, OutActivity.class);
                startActivity(intent);
                finish();
            } else if (msg.what == 6) {
                Toast.makeText(AddOutActivity.this, "失败", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public void addOut() {
        Outstorage outstorage = new Outstorage();
        outstorage.setDepartmentid(Integer.parseInt(dept.getText().toString().split("-")[0]));
        outstorage.setCompanyid(Integer.parseInt(company.getText().toString().split("-")[0]));
        outstorage.setLinkmanid(linkman.getText().toString().split("-")[1]);
        outstorage.setPhone(linkman.getText().toString().split("-")[1]);
        outstorage.setGoodsids(name.getText().toString().split("-")[0]);
        outstorage.setType(Integer.parseInt(type.getText().toString().split("-")[0]));
        outstorage.setAmount(amount.getText().toString());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String s = HttpUtil.postRequestJSON("/out/addOutstorageInfo", JsonUtils.object2String(outstorage));
                    Message message = Message.obtain();
                    if (JsonUtils.string2Object(s, HashMap.class).get("msg").toString().equals("success")) {
                        message.what = 5;
                    } else {
                        message.what = 6;
                    }
                    myhandler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}