package com.ltdd.weatherforecastapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.ltdd.weatherforecastapp.R;
import com.ltdd.weatherforecastapp.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    //
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        HomeViewModel homeViewModel =
//                new ViewModelProvider(this).get(HomeViewModel.class);
//
//        binding = FragmentHomeBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();
//
////        final TextView textView = binding.textHome;
////        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
//        return root;
//    }
    private View mView;
    private RecyclerView rvWeather;
    private GridView rvDetail2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);

        rvWeather = mView.findViewById(R.id.rv_detail_hourly);
        HourlyWeatherAdapter hourlyWeatherAdapter = new HourlyWeatherAdapter(this.getActivity(), getListDetailHourlyWeather());
        rvWeather.setAdapter(hourlyWeatherAdapter);

        rvDetail2 = mView.findViewById(R.id.rv_detail1);
//        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(this.getActivity(), 2);
//        rvDetail2.setLayoutManager(gridLayoutManager2);

//        final GridView gridView = (GridView) findViewById(R.id.gridView);
//        gridView.setAdapter(new CustomGridAdapter(this, image_details));

        DetailAdapter adapter2 = new DetailAdapter(this.getActivity(), getListDetail2());
        rvDetail2.setAdapter(adapter2);

        return mView;
    }

    private ArrayList<WeatherRVModel> getListDetailHourlyWeather() {
        ArrayList<WeatherRVModel> weatherList = new ArrayList<>();
        weatherList.add(new WeatherRVModel("3:00", "sun", "30"));
        weatherList.add(new WeatherRVModel("4:00", "sun", "30"));
        weatherList.add(new WeatherRVModel("5:00", "sun", "30"));
        weatherList.add(new WeatherRVModel("6:00", "sun", "30"));
        weatherList.add(new WeatherRVModel("7:00", "sun", "30"));
        weatherList.add(new WeatherRVModel("8:00", "sun", "30"));
        weatherList.add(new WeatherRVModel("9:00", "sun", "30"));
        weatherList.add(new WeatherRVModel("10:00", "sun", "30"));
        weatherList.add(new WeatherRVModel("11:00", "sun", "30"));
        weatherList.add(new WeatherRVModel("12:00", "sun", "30"));
        return weatherList;
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}