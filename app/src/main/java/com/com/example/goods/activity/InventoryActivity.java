package com.com.example.goods.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.com.example.goods.BaseActivity;
import com.com.example.goods.R;
import com.com.example.goods.adapter.GoodsDataAdapter;
import com.com.example.goods.adapter.InventoryAdapter;
import com.com.example.goods.entity.Goods;
import com.com.example.goods.entity.Inv;
import com.com.example.goods.entity.Inventory;
import com.com.example.goods.utils.HttpUtil;
import com.com.example.goods.utils.JsonUtils;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kotlin.jvm.functions.FunctionN;

public class InventoryActivity extends BaseActivity implements View.OnClickListener {
    private EditText ed_search;
    //数据源
    List<Inv> data = new ArrayList<>();
    private ListView inventoryList;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        getInv();
        initView();
        init();
        initEvent();
    }

    @Override
    public void init() {

    }

    @Override
    public void initView() {
        inventoryList = findViewById(R.id.invlist);
        back = findViewById(R.id.back);
        ed_search = findViewById(R.id.ED_search);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        back.setOnClickListener(this);
        changeListener();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    public void getInv() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String s = HttpUtil.postRequest("/inventory/getInventory/{}");
                    ArrayList arrayList = JsonUtils.string2Object(JsonUtils.string2Object(s, HashMap.class).get("lists").toString(), ArrayList.class);
                    for (Object o : arrayList) {
                        System.out.println(o);
                        Inv inv = JsonUtils.string2Object(o.toString(), Inv.class);
                        data.add(inv);
                        Message message = Message.obtain();
                        message.what = 1;
                        myhandler.sendMessage(message);
                    }
                    for (Inv datum : data) {
                        System.out.println(datum);
                    }
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
                InventoryAdapter inventoryAdapter = new InventoryAdapter(InventoryActivity.this, data);
                inventoryList.setAdapter(inventoryAdapter);
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
                InventoryAdapter adapter = (InventoryAdapter) inventoryList.getAdapter();
                List<Inv> newdata = new ArrayList<>();
                for (Inv datum : data) {
                    if (datum.toString().contains(s)) {
                        newdata.add(datum);
                    }
                }
                adapter.setData(newdata);
                inventoryList.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}