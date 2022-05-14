package com.ltdd.weatherforecastapp.ui.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.ltdd.weatherforecastapp.Common.Common;
import com.ltdd.weatherforecastapp.R;
import com.ltdd.weatherforecastapp.SettingsActivity;
import com.ltdd.weatherforecastapp.api.ApiService;
import com.ltdd.weatherforecastapp.databinding.FragmentHomeBinding;
import com.ltdd.weatherforecastapp.model.Current;
import com.ltdd.weatherforecastapp.model.Daily;
import com.ltdd.weatherforecastapp.model.Hourly;
import com.ltdd.weatherforecastapp.model.WeatherForecastResponse;
import com.ltdd.weatherforecastapp.ui.search.SearchFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class HomeFragment extends Fragment {
    private int REQUEST_CODE_LOCATION_PERMISSION = 1;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private FragmentHomeBinding binding;
    private View mView;
    private RecyclerView rvWeather;
    private ListView lvDetailWeek;
    private GridView rvDetail;
    private TextView nameCity, tempCity, dateCity, description, sunrise, sunset;
    private String nameCityDefault = "Ho Chi Minh City";
    DetailAdapter adapter;
    HourlyWeatherAdapter hourlyWeatherAdapter;
    WeatherDayOfWeekAdapter weatherDayOfWeekAdapter;
    List<DetailWeather> list = new ArrayList<>();
    List<WeatherRVModel> weatherList = new ArrayList<>();
    List<WeatherDayOfWeek> weekList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);

        anhXa();

        rvDetail = mView.findViewById(R.id.rv_detail1);
        adapter = new DetailAdapter(this.getActivity(), list);
        rvDetail.setAdapter(adapter);

        rvWeather = mView.findViewById(R.id.rv_detail_hourly);
        hourlyWeatherAdapter = new HourlyWeatherAdapter(this.getActivity(), weatherList);
        rvWeather.setAdapter(hourlyWeatherAdapter);

        lvDetailWeek = mView.findViewById(R.id.lv_detail_weekly);
        weatherDayOfWeekAdapter = new WeatherDayOfWeekAdapter(this.getActivity(), weekList);
        lvDetailWeek.setAdapter(weatherDayOfWeekAdapter);

        getWeatherDetail();


        ImageButton imgbtsetting = (ImageButton) mView.findViewById(R.id.imgbt_setting);
        imgbtsetting.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intentLoadNewActivity =  new Intent(HomeFragment.this.getActivity(), SettingsActivity.class);
                HomeFragment.this.startActivity(intentLoadNewActivity);
            }
        });

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.getActivity());
        ImageButton imgbtlocation = (ImageButton) mView.findViewById(R.id.current_location);
        imgbtlocation.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                if(ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_CODE_LOCATION_PERMISSION);
                }else {
                    getCurrentLocation();
                }
            }
        });

        return mView;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE_LOCATION_PERMISSION & grantResults.length>0){
            getCurrentLocation();
        }else {
            Toast.makeText(this.getActivity(), "Permission Denied!", Toast.LENGTH_SHORT).show();
        }
    }

    private void  getCurrentLocation(){
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ActivityCompat.checkSelfPermission(this.getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.getActivity(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.getFusedLocationProviderClient(HomeFragment.this.getActivity())
                .requestLocationUpdates(locationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(@NonNull LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(HomeFragment.this.getActivity())
                                .removeLocationUpdates(this);
                        if (locationResult != null && locationResult.getLocations().size() > 0) {
                            int latestLocationIndex = locationResult.getLocations().size() - 1;
                            double latitude =
                                    locationResult.getLocations().get(latestLocationIndex).getLatitude();
                            double longitude = locationResult.getLocations().get(latestLocationIndex).getLongitude();
                            Common.latitude = String.valueOf(latitude);
                            Common.longitude = String.valueOf(longitude);
                            Common.nameCity = String.valueOf(nameCity.getText());
                            getWeatherDetail();
                        }
                    }
                }, Looper.getMainLooper());
    }

    private void anhXa() {
        nameCity = mView.findViewById(R.id.tv_name_city);
        tempCity = mView.findViewById(R.id.tv_temp);
        dateCity = mView.findViewById(R.id.tv_date);
        description = mView.findViewById(R.id.tv_description);
        sunrise = mView.findViewById(R.id.tv_sunrise);
        sunset = mView.findViewById(R.id.tv_sunset);
    }

//    public void receiveDataFromSearchFragment(String rLat, String rLon){
//        lat = rLat;
//        lon = rLon;
//    }

    private Date setTypeDay(String s){
        long l = Long.parseLong(s);
        Date date = new Date(l*1000L);
        return date;
    }

    private void getWeatherDetail() {
        ApiService.apiservice.getWeatherForecast(Common.latitude, Common.longitude,
                "6c496cba030fbaa54828d14c88c1de64", "minutely,alert", "vi",
                "metric").enqueue(new Callback<WeatherForecastResponse>() {
            @Override
            public void onResponse(Call<WeatherForecastResponse> call, retrofit2.Response<WeatherForecastResponse> response) {
                if(response.code() == 200) {
                    WeatherForecastResponse weatherResponse = response.body();

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    SimpleDateFormat getHourFormat = new SimpleDateFormat("HH:mm");
                    SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("EEEE");

//                    //Lấy thành phố hiện tại
//                    String city = weatherResponse.
                    //Nhiệt độ hiện tại
                    String temp = Math.round(weatherResponse.getCurrent().getTemp()) + "°C";
                    //Ngày hôm nay
                    String dateraw = String.valueOf(weatherResponse.getCurrent().getDt());
                    String date = simpleDateFormat.format(setTypeDay(dateraw));
                    //mô tả thời tiết hôm nay
                    String des = weatherResponse.getCurrent().getWeather().get(0).getDescription();
                    //thời gian mặt trời mọc
                    String sunriseRaw = String.valueOf(weatherResponse.getCurrent().getSunrise());
                    String rise = getHourFormat.format(setTypeDay(sunriseRaw));
                    //thời gian mặt trời lặn
                    String sunsetRaw = String.valueOf(weatherResponse.getCurrent().getSunset());
                    String set = getHourFormat.format(setTypeDay(sunsetRaw));

                    tempCity.setText(temp);
                    nameCity.setText(Common.nameCity);
                    dateCity.setText(date);
                    description.setText("Có " + des);
                    sunrise.setText(rise);
                    sunset.setText(set);

                    //thời tiết theo giờ
                    List<Hourly> hour = weatherResponse.getHourly();
                    for (int i=0; i < 24; i++) {

                        String s = String.valueOf(hour.get(i).getDt());

                        long l1 = Long.parseLong(s);
                        Date hour1 = new Date(l1*1000L);
                        //giờ
                        String hourly = getHourFormat.format(hour1);
                        //nhiệt độ
                        String t = Math.round(hour.get(i).getTemp()) + "°";
                        //icon
                        String icon = hour.get(i).getWeather().get(0).getIcon();
                        String ic = "_" + icon;

                        weatherList.add(new WeatherRVModel(hourly, ic, t));
                    }
                    hourlyWeatherAdapter.notifyDataSetChanged();

                    //thời tiết 7 ngày
                    List<Daily> daily = weatherResponse.getDaily();
                    for (int e=0; e < 7; e++) {
                        //ngày
                        String d1 = String.valueOf(daily.get(e).getDt());
                        String daily1 = simpleDateFormat1.format(setTypeDay(d1));
                        String daily2 = simpleDateFormat.format(setTypeDay(d1));
                        //icon
                        String icon = daily.get(e).getWeather().get(0).getIcon();
                        String ic = "_" + icon;
                        //nhiệt độ
                        String tempMax = Math.round(daily.get(e).getTemp().getMax()) + "°C";
                        String tempMin = Math.round(daily.get(e).getTemp().getMin()) + "°C";

                        weekList.add(new WeatherDayOfWeek(daily1,daily2, ic, tempMax, tempMin));
                    }
                    weatherDayOfWeekAdapter.notifyDataSetChanged();

                    //chỉ số uv
                    String uvi = String.valueOf(daily.get(0).getUvi());
                    //lượng mưa
                    String rain = daily.get(0).getRain() + " cm";
                    //nhiệt độ
                    String tempMaxInDay = Math.round(daily.get(0).getTemp().getMax()) + " °C";
                    String tempMinInDay = Math.round(daily.get(0).getTemp().getMin()) + " °C";
                    //khả năng mưa
                    String pop = Math.round(hour.get(0).getPop()*100) + "%";

                    Current current = weatherResponse.getCurrent();
                    //nhiệt độ cảm nhận
                    String feelslike = Math.round(current.getFeels_like()) + "°C";
                    //tốc độ gió
                    String wind = weatherResponse.getCurrent().getWind_speed() + " km/h";
                    //hướng gió
                    int windDeg = weatherResponse.getCurrent().getWind_deg();
                    String deg = "";
                    if (windDeg > 338 && windDeg <= 22)
                        deg = "Bắc";
                    else if (windDeg > 22 && windDeg <= 66)
                        deg = "Đông Bắc";
                    else if (windDeg > 66 && windDeg <= 111)
                        deg = "Đông";
                    else if (windDeg > 111 && windDeg <= 157)
                        deg = "Đông Nam";
                    else if (windDeg > 157 && windDeg <= 202)
                        deg = "Nam";
                    else if (windDeg > 202 && windDeg <= 247)
                        deg = "Tây Nam";
                    else if (windDeg > 247 && windDeg <= 291)
                        deg = "Tây";
                    else
                        deg = "Tây Bắc";

                    //tầm nhìn xa
                    String visibility = weatherResponse.getCurrent().getVisibility()/1000 + " km";
                    //độ ẩm
                    String humidity = weatherResponse.getCurrent().getHumidity() + "%";
                    //áp suất
                    String pressure = weatherResponse.getCurrent().getPressure() + " hPa";

                    list.add(new DetailWeather("Nhiệt độ cao nhất", tempMaxInDay,
                            "Thấp nhất: ", tempMinInDay));
                    list.add(new DetailWeather("Nhiệt độ cảm nhận", feelslike,
                            "Hiện tại: ", temp));
                    list.add(new DetailWeather("Khả năng mưa", pop,
                            "Lượng mưa: ", rain));
                    list.add(new DetailWeather("Gió", wind,
                            "Hướng: ", deg));
                    list.add(new DetailWeather("Tầm nhìn xa", visibility,
                            "Độ ẩm: ", humidity));
                    list.add(new DetailWeather("Chỉ số UV", uvi,
                            "Áp suất: ", pressure));

                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<WeatherForecastResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}