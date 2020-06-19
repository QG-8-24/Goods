package com.com.example.goods.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.com.example.goods.R;
import com.com.example.goods.entity.Inv;
import com.com.example.goods.entity.Inventory;
import com.com.example.goods.entity.Type;

import java.util.List;

public class InventoryAdapter extends BaseAdapter {
    //数据源
    List<Inv> data;
    //放射器
    LayoutInflater inflater;

    public List<Inv> getData() {
        return data;
    }

    public void setData(List<Inv> data) {
        this.data = data;
    }

    public InventoryAdapter(Context context, List<Inv> data) {
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
        View view = inflater.inflate(R.layout.inventroyitem, null);
        Inv inv = data.get(position);
        TextView name = view.findViewById(R.id.name);
        TextView producer = view.findViewById(R.id.producer);
        TextView specifications = view.findViewById(R.id.specifications);
        TextView amount = view.findViewById(R.id.amount);
        TextView type = view.findViewById(R.id.type);
        name.setText(inv.getName());
        producer.setText(inv.getProducer());
        specifications.setText(inv.getSpecifications());
        amount.setText(inv.getAmount().toString());
        type.setText(inv.getTypeName());
        return view;
    }

}
