package com.com.example.goods.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.com.example.goods.R;
import com.com.example.goods.entity.Instorage;
import com.com.example.goods.entity.Outstorage;

import java.util.List;

public class OutAdapter extends BaseAdapter {
    //数据源
    List<Outstorage> data;
    //放射器
    LayoutInflater inflater;

    public List<Outstorage> getData() {
        return data;
    }

    public void setData(List<Outstorage> data) {
        this.data = data;
    }

    public OutAdapter(Context context, List<Outstorage> data) {
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
        View view = inflater.inflate(R.layout.initem, null);
//        Outstorage outstorage = data.get(position);
//        TextView code = view.findViewById(R.id.code);
//        TextView intime = view.findViewById(R.id.intime);
//        TextView amount = view.findViewById(R.id.amount);
//        TextView department = view.findViewById(R.id.department);
//        TextView linkman = view.findViewById(R.id.linkman);
//        code.setText(outstorage.getType().toString()+outstorage.getCode());
//        intime.setText(outstorage.getIntime().toString());
////        amount.setText("物资总数:" + outstorage.getAmount());
//        department.setText("往来单位:" + outstorage.getCompany() + "-" + outstorage.getDepartment());
//        linkman.setText("联系人:" + outstorage.getLinkman());
        return view;
    }

}
