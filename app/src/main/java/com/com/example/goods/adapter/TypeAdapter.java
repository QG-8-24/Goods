package com.com.example.goods.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.com.example.goods.R;
import com.com.example.goods.entity.Type;

import java.util.List;

public class TypeAdapter extends BaseAdapter {
    //数据源
    List<Type> data;
    //放射器
    LayoutInflater inflater;

    public List<Type> getData() {
        return data;
    }

    public void setData(List<Type> data) {
        this.data = data;
    }

    public TypeAdapter(Context context, List<Type> data) {
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
        View view = inflater.inflate(R.layout.type_item, null);
        TextView typemsg = view.findViewById(R.id.msg);
        Type type = data.get(position);
        typemsg.setText(type.getName());
        return view;
    }

}
