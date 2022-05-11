package com.ltdd.weatherforecastapp.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ltdd.weatherforecastapp.R;

import java.util.List;

public class DetailAdapter extends BaseAdapter {

    private List<DetailWeather> lst;
    Context context;
    LayoutInflater inflater;

    public DetailAdapter(Context appContext, List<DetailWeather> listDetail) {
        context = appContext;
        lst = listDetail;
        inflater = LayoutInflater.from(appContext);
    }

    @Override
    public int getCount() {
        return lst.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.item_detail, null);
        TextView name = (TextView) view.findViewById(R.id.tv_name2);
        name.setText(lst.get(i).getName());

        TextView content = (TextView) view.findViewById(R.id.tv_content2);
        content.setText(lst.get(i).getContent());

        TextView titleSubContent = (TextView) view.findViewById(R.id.tv_title_subcontent);
        titleSubContent.setText(lst.get(i).getTitleSubContent());

        TextView subContent = (TextView) view.findViewById(R.id.tv_subcontent);
        subContent.setText(lst.get(i).getSubContent());
        return view;
    }
}
