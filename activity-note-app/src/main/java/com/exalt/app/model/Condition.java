package com.exalt.app.model;

import javax.persistence.*;

import javax.persistence.Entity;
import java.time.DayOfWeek;


//mark class as an Entity
@Entity

public class Condition extends BaseModel {

    private Boolean isNegative = false;
    private WeatherMode weatherMode;
    private DayOfWeek day;

    @OneToOne
    @JoinColumn
    private Location location;


    public Boolean isNegative() {
        return isNegative;
    }

    public void setNegative(Boolean isNegative) {
        this.isNegative = isNegative;
    }

    public WeatherMode getWeatherMode() {
        return weatherMode;
    }

    public void setWeatherMode(WeatherMode weatherMode) {
        this.weatherMode = weatherMode;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
    }
}
