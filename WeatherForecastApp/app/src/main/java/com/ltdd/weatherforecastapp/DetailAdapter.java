package com.ltdd.weatherforecastapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailViewHolder> {

    private List<DetailWeather> listDetail;
    Context c;

    public DetailAdapter(Context c, List<DetailWeather> listDetail) {
        this.c = c;
        this.listDetail = listDetail;
    }

    @NonNull
    @Override
    public DetailAdapter.DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail,parent,false);
        return new DetailAdapter.DetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailAdapter.DetailViewHolder holder, int position) {
        DetailWeather detail_weather = listDetail.get(position);

        if (detail_weather == null) {
            return;
        }

        holder.tvNameDetail2.setText(detail_weather.getName());
        holder.tvContent2.setText(detail_weather.getContent());
        holder.tvTitleSubContent.setText(detail_weather.getTitleSubContent());
        holder.tvSubContent.setText(detail_weather.getSubContent());
    }

    @Override
    public int getItemCount() {
        if (listDetail != null) {
            return listDetail.size();
        }
        return 0;
    }

    public class DetailViewHolder extends RecyclerView.ViewHolder {

        private TextView tvNameDetail2, tvContent2, tvTitleSubContent, tvSubContent;

        public DetailViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNameDetail2 = itemView.findViewById(R.id.tv_name2);
            tvContent2 = itemView.findViewById(R.id.tv_content2);
            tvTitleSubContent = itemView.findViewById(R.id.tv_title_subcontent);
            tvSubContent = itemView.findViewById(R.id.tv_subcontent);
        }
    }
}
