package com.wc.android.demo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


/**
 * 城市适配器
 */
@SuppressLint("InflateParams")
public class CityAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<City> infos;

    public CityAdapter(Context context, List<City> infos) {
        super();
        this.mInflater = LayoutInflater.from(context);
        this.infos = infos;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return infos.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return infos.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    class MyHoler {
        TextView tv_name;
        TextView tv_id;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyHoler holder = null;
        // 复用
        if (convertView != null) {
            holder = (MyHoler) convertView.getTag();
        } else {
            holder = new MyHoler();
            convertView = mInflater.inflate(R.layout.list_ietm_city, null);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_id = (TextView) convertView.findViewById(R.id.tv_id);
            convertView.setTag(holder);
        }
        City city = infos.get(position);
        holder.tv_name.setText(city.name);
        holder.tv_id.setText(city.id);
        return convertView;
    }
}
