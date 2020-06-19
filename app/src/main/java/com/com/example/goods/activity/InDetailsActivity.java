package com.com.example.goods.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.com.example.goods.BaseActivity;
import com.com.example.goods.R;
import com.com.example.goods.entity.vo.Instorage;
import com.com.example.goods.utils.JsonUtils;

import java.util.HashMap;
import java.util.List;

public class InDetailsActivity extends BaseActivity implements View.OnClickListener {
    private Instorage instorage;
    private HashMap<String, Object> goodsmsg = new HashMap<>();
    private ImageView back;
    private TextView type;
    private TextView company;
    private TextView dept;
    private TextView linkman;
    private TextView phone;
    private TextView name;
    private TextView amount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_details);
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
        type = findViewById(R.id.inv_type);
        type.setText(goodsmsg.get("typeName").toString());
        company = findViewById(R.id.inv_company);
        company.setText(instorage.getCompany());
        dept = findViewById(R.id.inv_department);
        dept.setText(instorage.getDepartment());
        linkman = findViewById(R.id.inv_linkman);
        linkman.setText(instorage.getLinkman());
        phone = findViewById(R.id.inv_phone);
        phone.setText(instorage.getPhone());
        name = findViewById(R.id.inv_name);
        name.setText(goodsmsg.get("name").toString());
        amount = findViewById(R.id.inv_amount);
        amount.setText(instorage.getAmountList().get(0).toString());
    }

    @Override
    public void initData() {
        instorage = JsonUtils.string2Object(getIntent().getStringExtra("msg"), Instorage.class);
        List<HashMap<String, Object>> goodsidsList = instorage.getGoodsList();
        goodsmsg = goodsidsList.get(0);

    }

    @Override
    public void initEvent() {
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
}