package com.com.example.goods.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.com.example.goods.BaseActivity;
import com.com.example.goods.R;
import com.com.example.goods.adapter.InAdapter;
import com.com.example.goods.adapter.OutAdapter;
import com.com.example.goods.entity.Instorage;
import com.com.example.goods.entity.Outstorage;
import com.com.example.goods.utils.HttpUtil;
import com.com.example.goods.utils.JsonUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OutActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private ArrayList<Outstorage> list;
    private ListView outlist;
    private ImageView back;
    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out);
        initView();
        getoutList();
        init();
        initEvent();
    }

    @Override
    public void init() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String format = df.format(new Date());
        Date parse = null;
        try {
            parse = df.parse(format);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        Outstorage outstorage = new Outstorage(1, "R001", "XXX", "XXXxxxxxxxxxxxxxxxxxxxxx", "13066780987", parse, 1, "MQ", "1/2/3/4/5/6", "30");
//        Outstorage outstorage1 = new Outstorage(1, "R002", "XXX", "XXX", "13066780987", parse, 1, "MQ", "1/2/3", "30");
//        Outstorage outstorage2 = new Outstorage(1, "R003", "XXX", "XXX", "13066780987", parse, 1, "MQ", "1/2/3", "30");
//        list = new ArrayList<>();
//        list.add(outstorage);
//        list.add(outstorage1);
//        list.add(outstorage2);
//        OutAdapter outAdapter = new OutAdapter(OutActivity.this, list);
//        outlist.setAdapter(outAdapter);
    }

    @Override
    public void initView() {
        outlist = findViewById(R.id.outlist);
        back = findViewById(R.id.back);
        add = findViewById(R.id.add);
    }

    @Override
    public void initData() {
        getoutList();
    }

    @Override
    public void initEvent() {
        outlist.setOnItemClickListener(this);
        back.setOnClickListener(this);
        add.setOnClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        OutAdapter outAdapter = (OutAdapter) outlist.getAdapter();
        Outstorage outstorage = outAdapter.getData().get(position);
        Intent intent = new Intent(OutActivity.this, OutDetailsActivity.class);
        intent.putExtra("msg", JsonUtils.object2String(outstorage));  // 传递参数，根据需要填写*/
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                Intent intent = new Intent(OutActivity.this, AddOutActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    public void getoutList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String s = HttpUtil.postRequest("/out/getOutstorageInfo/{\"type\":\"0\",\"linkman\":null}");
                    System.out.println(s);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}