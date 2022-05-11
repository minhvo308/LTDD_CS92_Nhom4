package com.ltdd.weatherforecastapp.ui.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.ltdd.weatherforecastapp.R;

import java.util.List;

public class WeatherDayOfWeekAdapter extends BaseAdapter {

    private List<WeatherDayOfWeek> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public WeatherDayOfWeekAdapter(Context aContext, List<WeatherDayOfWeek> listData) {
        this.listData = listData;
        this.context = aContext;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        view = inflater.inflate(R.layout.item_detail_weekly, null);
//        ImageView icon = (ImageView) view.findViewById(R.id.iv_icon_day_of_week);
//        TextView day1 = (TextView) view.findViewById(R.id.tv_day_of_week1);
//        TextView day2 = (TextView) view.findViewById(R.id.tv_day_of_week2);
//        TextView tempMax = (TextView) view.findViewById(R.id.tv_temp_max_day);
//        TextView tempMin = (TextView) view.findViewById(R.id.tv_temp_min_day);
//
//        WeatherDayOfWeek weatherDayOfWeek = this.listData.get(position);
//
//        day1.setText(weatherDayOfWeek.getDay1());
//        day2.setText(weatherDayOfWeek.getDay2());
//        tempMax.setText(weatherDayOfWeek.getTempMax());
//        tempMin.setText(weatherDayOfWeek.getTempMin());
////
//        int imageId = this.getMipmapResIdByName(weatherDayOfWeek.getIcon());
//
//        icon.setImageResource(imageId);
//        return view;

        ViewHolder holder;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_detail_weekly, null);
            holder = new ViewHolder();
            holder.icon = (ImageView) view.findViewById(R.id.iv_icon_day_of_week);
            holder.day = (TextView) view.findViewById(R.id.tv_day_of_week1);
            holder.day2 = (TextView) view.findViewById(R.id.tv_day_of_week2);
            holder.tempMax = (TextView) view.findViewById(R.id.tv_temp_max_day);
            holder.tempMin = (TextView) view.findViewById(R.id.tv_temp_min_day);

            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

        WeatherDayOfWeek weatherDayOfWeek = this.listData.get(position);
        holder.day.setText(weatherDayOfWeek.getDay1());
        holder.day2.setText(weatherDayOfWeek.getDay2());
        holder.tempMax.setText(weatherDayOfWeek.getTempMax());
        holder.tempMin.setText(weatherDayOfWeek.getTempMin());

        int imageId = this.getMipmapResIdByName(weatherDayOfWeek.getIcon());

        holder.icon.setImageResource(imageId);

        return view;
    }

    // Find Image ID corresponding to the name of the image (in the directory mipmap).
    public int getMipmapResIdByName(String resName)  {
        String pkgName = context.getPackageName();
        // Return 0 if not found.
        int resID = context.getResources().getIdentifier(resName , "mipmap", pkgName);
        return resID;
    }

    static class ViewHolder {
        ImageView icon;
        TextView day,day2, tempMax, tempMin;
    }
}
