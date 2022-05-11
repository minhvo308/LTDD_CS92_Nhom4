package com.ltdd.weatherforecastapp.ui.home;

public class WeatherDayOfWeek {
    private String day1;
    private String day2;
    private String icon;
    private String tempMax;
    private String tempMin;

    public WeatherDayOfWeek(String day1, String day2, String icon, String tempMax, String tempMin) {
        this.setDay1(day1);
        this.setDay2(day2);
        this.setIcon(icon);
        this.setTempMax(tempMax);
        this.setTempMin(tempMin);
    }


    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTempMax() {
        return tempMax;
    }

    public void setTempMax(String tempMax) {
        this.tempMax = tempMax;
    }

    public String getTempMin() {
        return tempMin;
    }

    public void setTempMin(String tempMin) {
        this.tempMin = tempMin;
    }

    public String getDay1() {
        return day1;
    }

    public void setDay1(String day1) {
        this.day1 = day1;
    }

    public String getDay2() {
        return day2;
    }

    public void setDay2(String day2) {
        this.day2 = day2;
    }
}
