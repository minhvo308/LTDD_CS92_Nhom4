package com.ltdd.weatherforecastapp.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.ltdd.weatherforecastapp.R;

import java.util.ArrayList;
import java.util.List;

public class HourlyWeatherAdapter extends RecyclerView.Adapter<HourlyWeatherAdapter.WeatherViewHolder> {
    Context context;
    private List<WeatherRVModel> weatherRVModelArrayList;

    public HourlyWeatherAdapter(Context context, List<WeatherRVModel> weatherRVModelArrayList) {
        this.context = context;
        this.weatherRVModelArrayList = weatherRVModelArrayList;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_detail_weather_hourly, parent, false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        WeatherRVModel modal = weatherRVModelArrayList.get(position);
        holder.tvTime.setText(modal.getTime());
        holder.tvTemperature.setText(modal.getTemp() + "C");

        int imageId = this.getMipmapResIdByName(modal.getImg());

        holder.imgIconWeather.setImageResource(imageId);
    }

    @Override
    public int getItemCount() {
        return weatherRVModelArrayList.size();
    }

    public class WeatherViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTime, tvTemperature;
        private ImageView imgIconWeather;
        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tv_time);
            imgIconWeather = itemView.findViewById(R.id.img_icon_weather);
            tvTemperature = itemView.findViewById(R.id.tv_temperature);
        }
    }

    // Find Image ID corresponding to the name of the image (in the directory mipmap).
    public int getMipmapResIdByName(String icon)  {
        String pkgName = context.getPackageName();
        // Return 0 if not found.
        int iconID = context.getResources().getIdentifier(icon , "mipmap", pkgName);
        return iconID;
    }
}
