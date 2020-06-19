package com.com.example.goods.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.SmsManager;
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
import com.com.example.goods.adapter.InAdapter;
import com.com.example.goods.adapter.InventoryAdapter;
import com.com.example.goods.entity.Goods;
import com.com.example.goods.entity.vo.Instorage;
import com.com.example.goods.entity.Inv;
import com.com.example.goods.utils.HttpUtil;
import com.com.example.goods.utils.JsonUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class InActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private EditText ed_search;
    private ArrayList<Instorage> datalist = new ArrayList<>();
    private ListView inlist;
    private ImageView back;
    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in);
        initView();
        initData();
        init();
        initEvent();
    }

    @Override
    public void init() {
    }

    @Override
    public void initView() {
        inlist = findViewById(R.id.inlist);
        back = findViewById(R.id.back);
        add = findViewById(R.id.add);
        ed_search = findViewById(R.id.ED_search);
    }

    @Override
    public void initData() {
        initList();
    }

    @Override
    public void initEvent() {
        inlist.setOnItemClickListener(this);
        back.setOnClickListener(this);
        add.setOnClickListener(this);
        changeListener();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        InAdapter inAdapter = (InAdapter) inlist.getAdapter();
        Instorage instorage = inAdapter.getData().get(position);
        Intent intent = new Intent(InActivity.this, InDetailsActivity.class);
        intent.putExtra("msg", JsonUtils.object2String(instorage));  // 传递参数，根据需要填写*/
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                Intent intent = new Intent(InActivity.this, AddInActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    /**
     * 初始化数据
     */
    public void initList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String s = HttpUtil.postRequest("/in/getInstorageInfo/{\"type\":\"0\",\"linkman\":null}");
                    HashMap hashMap = JsonUtils.string2Object(s, HashMap.class);
                    ArrayList instorage = JsonUtils.string2Object(hashMap.get("instorage").toString(), ArrayList.class);
                    for (Object o : instorage) {
                        String string = JsonUtils.object2String(o);
                        Instorage instorage1 = JsonUtils.string2Object(string, Instorage.class);
                        datalist.add(instorage1);
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

    //search 监听
    public void changeListener() {
        ed_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                InAdapter adapter = (InAdapter) inlist.getAdapter();
                ArrayList<Instorage> newlist = new ArrayList<>();
                for (Instorage instorage : datalist) {
                    if (instorage.toString().contains(s)) {
                        newlist.add(instorage);
                    }
                }
                adapter.setData(newlist);
                inlist.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //UIHandler
    private Handler myhandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 1) {
                InAdapter adapter = new InAdapter(InActivity.this, datalist);
                inlist.setAdapter(adapter);
            }
        }
    };
}