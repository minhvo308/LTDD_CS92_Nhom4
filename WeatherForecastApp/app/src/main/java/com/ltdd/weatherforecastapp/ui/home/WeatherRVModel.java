package com.ltdd.weatherforecastapp.ui.home;

public class WeatherRVModel {
    private String time;
    private String img;
    private String temp;

    public WeatherRVModel(String time, String img, String temp) {
        this.time = time;
        this.img = img;
        this.temp = temp;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }
}
