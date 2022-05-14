package com.ltdd.weatherforecastapp.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ltdd.weatherforecastapp.model.WeatherForecastResponse;
import com.ltdd.weatherforecastapp.model.WeatherResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    //link API: https://api.openweathermap.org/data/2.5/onecall?lat=10.762622&lon=106.660172&lang=vi&exclude=minutely,alerts&units=metric&appid=6c496cba030fbaa54828d14c88c1de64
    Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create();
    ApiService apiservice = new Retrofit.Builder().baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create(gson)).build().create(ApiService.class);

    @GET("data/2.5/onecall")
    Call<WeatherForecastResponse> getWeatherForecast(@Query("lat") String lat,
                                                     @Query("lon") String lon,
                                                     @Query("appid") String appId,
                                                     @Query("exclude") String exclude,
                                                     @Query("lang") String lang,
                                                     @Query("units") String unit);

    @GET("data/2.5/weather")
    Call<WeatherResponse> getWeatherByName(@Query("q") String q,
                                           @Query("appid") String appId,
                                           @Query("exclude") String exclude,
                                           @Query("lang") String lang,
                                           @Query("units") String unit);
}
