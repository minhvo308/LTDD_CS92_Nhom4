package com.ltdd.weatherforecastapp.ui.search;

import java.io.Serializable;

public class SearchModel implements Serializable {
    private String nameCity;
    private String kw;
    private String tempCity;
    private String iconCity;

    public SearchModel(String nameCity, String kw, String tempCity, String iconCity) {
        this.nameCity = nameCity;
        this.kw = kw;
        this.tempCity = tempCity;
        this.iconCity = iconCity;
    }

    public String getNameCity() {
        return nameCity;
    }

    public void setNameCity(String nameCity) {
        this.nameCity = nameCity;
    }

    public String getTempCity() {
        return tempCity;
    }

    public void setTempCity(String tempCity) {
        this.tempCity = tempCity;
    }

    public String getIconCity() {
        return iconCity;
    }

    public void setIconCity(String iconCity) {
        this.iconCity = iconCity;
    }

    public String getKw() {
        return kw;
    }

    public void setKw(String kw) {
        this.kw = kw;
    }
}
