package com.com.example.goods.activity;

import androidx.annotation.NonNull;

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

import com.com.example.goods.BaseActivity;
import com.com.example.goods.R;
import com.com.example.goods.adapter.GoodsDataAdapter;
import com.com.example.goods.entity.Goods;
import com.com.example.goods.utils.HttpUtil;
import com.com.example.goods.utils.JsonUtils;

import java.io.IOException;
import java.util.ArrayList;

public class GoodsDataActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private EditText ed_search;
    private ArrayList<Goods> list;
    private ListView goodsDataList;
    private ImageView back;
    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_data);
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
        goodsDataList = findViewById(R.id.goodsdatalist);
        back = findViewById(R.id.back);
        add = findViewById(R.id.add);
        ed_search = findViewById(R.id.ED_search);
    }

    @Override
    public void initData() {
        getAll();
    }

    @Override
    public void initEvent() {
        goodsDataList.setOnItemClickListener(this);
        back.setOnClickListener(this);
        add.setOnClickListener(this);
        changeListener();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        GoodsDataAdapter adapter = (GoodsDataAdapter) goodsDataList.getAdapter();
        Goods goods = adapter.getData().get(position);
        Intent intent = new Intent(GoodsDataActivity.this, GoodsDataUpdateActivity.class);
        intent.putExtra("msg", JsonUtils.object2String(goods));  // 传递参数，根据需要填写
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                Intent intent = new Intent(GoodsDataActivity.this, AddGoodsDataActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    public void getAll() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String s = HttpUtil.postRequest("/goods/getall");
                    ArrayList<Goods> arrayList = JsonUtils.string2Object(s, ArrayList.class);
                    list = new ArrayList<>();
                    for (Object o : arrayList) {
                        Goods goods = JsonUtils.string2Object(JsonUtils.object2String(o), Goods.class);
                        list.add(goods);
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
                GoodsDataAdapter goodsDataAdapter = new GoodsDataAdapter(GoodsDataActivity.this, list);
                goodsDataList.setAdapter(goodsDataAdapter);
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
                GoodsDataAdapter adapter = (GoodsDataAdapter) goodsDataList.getAdapter();
                ArrayList<Goods> data = new ArrayList<>();
                for (Goods goods : list) {
                    if (goods.toString().contains(s)) {
                        {
                            data.add(goods);
                        }
                    }
                }

                adapter.setData(data);
                goodsDataList.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}