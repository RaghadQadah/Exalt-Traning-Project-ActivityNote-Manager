package com.exalt.app.utils.adapter;

import com.exalt.app.model.Weather;

import java.time.DayOfWeek;
import java.util.Date;

public interface IDataAdapter {

    public Date getStartDate(String eventName, String locationName);

    public Coordinates getCoordinates(String locationName);

    public Weather getWeatherDetails(String locationName, DayOfWeek dayOfWeek);
}
