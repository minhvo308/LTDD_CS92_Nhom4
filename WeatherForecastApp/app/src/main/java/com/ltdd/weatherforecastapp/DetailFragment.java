package com.ltdd.weatherforecastapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class DetailFragment extends Fragment {

    private View mView;
    private RecyclerView rvDetail, rvDetail2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_detail, container, false);

        rvDetail2 = mView.findViewById(R.id.rv_detail1);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(this.getActivity(), 2);
        rvDetail2.setLayoutManager(gridLayoutManager2);

        DetailAdapter adapter2 = new DetailAdapter(this.getActivity(), getListDetail2());
        rvDetail2.setAdapter(adapter2);

        return mView;
    }

    private List<DetailWeather> getListDetail2() {
        List<DetailWeather> list = new ArrayList<>();
        list.add(new DetailWeather("Nhiệt độ cao nhất", "33°C", "Thấp nhất: ", "23°C"));
        list.add(new DetailWeather("Nhiệt độ cảm nhận", "29°C", "Hiện tại: ", "26°C"));
        list.add(new DetailWeather("Khả năng mưa", "30%", "Lượng mưa: ", "0,8cm"));
        list.add(new DetailWeather("Gió", "13 km/h", "Hướng: ", "Đông Nam"));
        list.add(new DetailWeather("Tầm nhìn xa", "9,7 km", "Độ ẩm: ", "80%"));
        list.add(new DetailWeather("Chỉ số UV", "4", "Áp suất: ", "1009 hPa"));
        return list;
    }
}