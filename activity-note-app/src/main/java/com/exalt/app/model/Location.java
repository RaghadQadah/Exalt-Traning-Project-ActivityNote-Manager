package com.exalt.app.model;
import javax.persistence.Entity;


@Entity
public class Location extends BaseModel {

    private String name;
    private String longitude;
    private String latitude;

    public static Location getDefault() {
        Location location = new Location();
        location.setName("Ramallah");
        return location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
