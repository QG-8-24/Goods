package com.com.example.goods.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.com.example.goods.R;
import com.com.example.goods.entity.Goods;
import com.com.example.goods.entity.Type;

import java.util.List;

public class GoodsDataAdapter extends BaseAdapter {
    //数据源
    List<Goods> data;
    //放射器
    LayoutInflater inflater;

    public List<Goods> getData() {
        return data;
    }

    public void setData(List<Goods> data) {
        this.data = data;
    }

    public GoodsDataAdapter(Context context, List<Goods> data) {
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
        View view = inflater.inflate(R.layout.goodsdataitem, null);
        TextView name = view.findViewById(R.id.name);
        TextView producer = view.findViewById(R.id.producer);
        TextView specifications = view.findViewById(R.id.specifications);
        Goods goods = data.get(position);
        name.setText(goods.getName());
        producer.setText(goods.getProducer());
        specifications.setText(goods.getSpecifications());
        return view;
    }

}
