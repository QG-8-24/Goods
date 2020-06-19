package com.com.example.goods.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.com.example.goods.R;
import com.com.example.goods.entity.Department;
import com.com.example.goods.entity.User;

import java.util.List;

public class DepartmentManageAdapter extends BaseAdapter {
    //数据源
    List<Department> data;
    //放射器
    LayoutInflater inflater;

    public List<Department> getData() {
        return data;
    }

    public void setData(List<Department> data) {
        this.data = data;
    }

    public DepartmentManageAdapter(Context context, List<Department> data) {
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
        View view = inflater.inflate(R.layout.departmentitem, null);
        TextView departmentmsg = view.findViewById(R.id.msg);
        Department department = data.get(position);
        departmentmsg.setText(department.getName());
        return view;
    }

}
