package com.com.example.goods.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.com.example.goods.R;
import com.com.example.goods.entity.vo.Instorage;
import com.com.example.goods.entity.Type;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.List;

public class InAdapter extends BaseAdapter {
    //数据源
    List<Instorage> data;
    //放射器
    LayoutInflater inflater;


    public List<Instorage> getData() {
        return data;
    }

    public void setData(List<Instorage> data) {
        this.data = data;
    }

    public InAdapter(Context context, List<Instorage> data) {
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
        Instorage instorage = data.get(position);
        TextView code = view.findViewById(R.id.code);
        TextView intime = view.findViewById(R.id.intime);
        TextView name = view.findViewById(R.id.name);
        TextView type = view.findViewById(R.id.type);
        TextView department = view.findViewById(R.id.department);
        TextView linkman = view.findViewById(R.id.linkman);
        code.setText(instorage.getCode());
        intime.setText(instorage.getIntime().toString());
        List<HashMap<String, Object>> goodsidsList = instorage.getGoodsList();
        HashMap<String, Object> goodsmsg = goodsidsList.get(0);
        name.setText(goodsmsg.get("name").toString());
        String typemsg = null;
        if (instorage.getType() == 1) {
            typemsg = "无偿捐款";
        } else if (instorage.getType() == 2) {
            typemsg = "上级下拨";
        } else if (instorage.getType() == 3) {
            typemsg = "自行采购";
        } else {
            typemsg = "采购退货";
        }
        type.setText("入库方式:" + typemsg);
        department.setText("往来单位:" + instorage.getCompany() + "-" + instorage.getDepartment());
        linkman.setText("联系人:" + instorage.getLinkman());
        return view;
    }

}
