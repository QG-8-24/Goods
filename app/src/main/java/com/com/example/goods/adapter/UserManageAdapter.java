package com.com.example.goods.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.com.example.goods.R;
import com.com.example.goods.entity.Company;
import com.com.example.goods.entity.Department;
import com.com.example.goods.entity.User;
import com.com.example.goods.utils.HttpUtil;

import java.util.HashMap;
import java.util.List;

public class UserManageAdapter extends BaseAdapter {
    //数据源
    List<User> data;
    HashMap<Integer, Company> chm;
    HashMap<Integer, Department> dhm;
    //放射器
    LayoutInflater inflater;

    public HashMap<Integer, Company> getChm() {
        return chm;
    }

    public void setChm(HashMap<Integer, Company> chm) {
        this.chm = chm;
    }

    public HashMap<Integer, Department> getDhm() {
        return dhm;
    }

    public void setDhm(HashMap<Integer, Department> dhm) {
        this.dhm = dhm;
    }

    public List<User> getData() {
        return data;
    }

    public void setData(List<User> data) {
        this.data = data;
    }

    public UserManageAdapter(Context context, List<User> data, HashMap<Integer, Company> chm,
                             HashMap<Integer, Department> dhm) {
        this.chm = chm;
        this.dhm = dhm;
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
        View view = inflater.inflate(R.layout.useritem, null);
        TextView usrmsg = view.findViewById(R.id.msg);
        User user = data.get(position);
        Department department = dhm.get(user.getDepartmentid());
        Company company = chm.get(department.getCompanyid());
        usrmsg.setText(company.getShortname() + "-" + department.getName() + "-" + user.getName());
        return view;
    }
}
