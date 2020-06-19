package com.com.example.goods.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.com.example.goods.R;
import com.com.example.goods.entity.Company;
import com.com.example.goods.entity.Department;

import java.util.List;

public class CompanyManageAdapter extends BaseAdapter {
    //数据源
    List<Company> data;
    //放射器
    LayoutInflater inflater;

    public List<Company> getData() {
        return data;
    }

    public void setData(List<Company> data) {
        this.data = data;
    }

    public CompanyManageAdapter(Context context, List<Company> data) {
        this.data = data;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.companyitem, null);
        TextView companymsg = view.findViewById(R.id.msg);
        Company company = data.get(position);
        companymsg.setText(company.getId() + "-" + company.getName());
        return view;
    }

}
