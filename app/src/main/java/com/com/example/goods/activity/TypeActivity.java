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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.com.example.goods.BaseActivity;
import com.com.example.goods.R;
import com.com.example.goods.adapter.TypeAdapter;
import com.com.example.goods.entity.Type;
import com.com.example.goods.utils.HttpUtil;
import com.com.example.goods.utils.JsonUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TypeActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private EditText ed_search;
    private ArrayList<Type> typeArrayList;
    private ListView typelist;
    private ImageView back;
    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);
        initView();
        init();
        initEvent();
        changeListener();
    }

    @Override
    public void init() {
        initTypeList();
    }

    @Override
    public void initView() {
        typelist = findViewById(R.id.typelist);
        back = findViewById(R.id.back);
        add = findViewById(R.id.add);
        ed_search = findViewById(R.id.ED_search);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        typelist.setOnItemClickListener(this);
        back.setOnClickListener(this);
        add.setOnClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TypeAdapter adapter = (TypeAdapter) typelist.getAdapter();
        final Type type = adapter.getData().get(position);
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("物资类别").setIcon(R.drawable.type)
                .setMessage(type.getName())
                .setPositiveButton("删除", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    String s = HttpUtil.postRequest("/type/delTypes/" + type.getId().toString());
                                    Message message=Message.obtain();
                                    if (JsonUtils.string2Object(s, HashMap.class).get("msg").equals("发生未知错误！")) {
                                       message.what=2;
                                    }else {
                                       message.what=3;
                                    }
                                    myhandler.sendMessage(message);
                                    initTypeList();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                })
                //进入修改页面
                .setNeutralButton("修改", new DialogInterface.OnClickListener() {//添加普通按钮
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(TypeActivity.this, TypeUpdateActivity.class);
                        intent.putExtra("msg", JsonUtils.object2String(type));  // 传递参数，根据需要填写
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
            case R.id.back:
                finish();
                break;
            case R.id.add:
                Intent intent2 = new Intent(TypeActivity.this, AddTypeActivity.class);
                startActivity(intent2);
                finish();
                break;
        }
    }

    public void initTypeList() {
        final ArrayList<Type> arrayList = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String request = HttpUtil.postRequest("/type/getTypes");
                    HashMap hashMap = JsonUtils.string2Object(request, HashMap.class);
                    ArrayList types = JsonUtils.string2Object(hashMap.get("types").toString(), ArrayList.class);
                    for (Object type : types) {
                        arrayList.add(JsonUtils.string2Object(type.toString(), Type.class));

                    }
                    typeArrayList = arrayList;
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
                TypeAdapter typeAdapter = new TypeAdapter(TypeActivity.this, typeArrayList);
                typelist.setAdapter(typeAdapter);
            }else if (msg.what==2){
                Toast.makeText(TypeActivity.this,"删除失败",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(TypeActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
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
                TypeAdapter adapter = (TypeAdapter) typelist.getAdapter();
                List<Type> data = typeArrayList;
                List<Type> newdata = new ArrayList<>();
                for (Type datum : data) {
                    if (datum.toString().contains(s)) {
                        newdata.add(datum);
                    }
                }
                adapter.setData(newdata);
                typelist.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}