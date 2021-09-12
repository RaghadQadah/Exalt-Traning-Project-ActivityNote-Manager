package com.exalt.app.utils.adapter;

public class Coordinates {

    private String latitude;
    private String longitude;

    public Coordinates() {

    }

    public Coordinates(String longitude, String latitude) {
        this();
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
