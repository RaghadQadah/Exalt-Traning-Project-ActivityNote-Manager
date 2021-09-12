package com.exalt.app.service.weather;

import com.exalt.app.model.Weather;
import com.exalt.app.service.crud.ICurdService;

import java.time.DayOfWeek;
import java.util.Map;

public interface IWeatherService extends ICurdService<Weather, Long> {

    public void addWeather(String city);

    /**
     * Gets all weathers mapped by DayOfWeek for a location by its name
     * @param locationName
     * @return
     */
    Map<DayOfWeek, Weather> getWeatherMap(String locationName);

    /**
     * Gets weather by dayOfWeek and location name
     * @param locationName
     * @param dayOfWeek
     * @return
     */
    Weather getWeather(String locationName, DayOfWeek dayOfWeek);
}
