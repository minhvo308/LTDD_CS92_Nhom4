package com.ltdd.weatherforecastapp.model;

public class Coord {
    private double lon;
    private double lat;

    public Coord(double lon, double lat) {
        this.setLon(lon);
        this.setLat(lat);
    }


    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
