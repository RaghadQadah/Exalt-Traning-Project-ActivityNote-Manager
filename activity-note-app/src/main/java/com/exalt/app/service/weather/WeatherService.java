package com.exalt.app.service.weather;

import com.exalt.app.model.Weather;
import com.exalt.app.repository.WeatherRepositroy;
import com.exalt.app.service.crud.AbstractCurdService;
import com.exalt.app.utils.Utils;
import com.exalt.app.utils.adapter.IDataAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.*;

@Service
public class WeatherService extends AbstractCurdService<Weather, Long> implements IWeatherService {

    @Autowired
    WeatherRepositroy weatherRepositroy;

    @Autowired
    IDataAdapter dataAdapter;

    public void addWeather(String city) {

        /**
         * TODO: use this logic from dataAdapter.
         */
        Calendar calendar = Calendar.getInstance();
        int dayNumber = calendar.get(Calendar.DAY_OF_WEEK);
        DayOfWeek day = DayOfWeek.of(dayNumber);
        Weather weather = dataAdapter.getWeatherDetails(city, day);
        weatherRepositroy.save(weather);

    }


    public List<Weather> getAllWeather() {
        List<Weather> weatherList = new ArrayList<Weather>();
        weatherRepositroy.findAll().forEach(weather -> weatherList.add(weather));
        return weatherList;
    }

    @Override
    public Map<DayOfWeek, Weather> getWeatherMap(String locationName) {
        List<Weather> weathers = weatherRepositroy.findAllByLocationName(locationName);
        Map<DayOfWeek, Weather> weathersMap = new HashMap<>();
        for (Weather weather : weathers) {
            weathersMap.put(Utils.getDayOfWeek(weather.getTimestamp()), weather);
        }
        return weathersMap;
    }

    @Override
    public Weather getWeather(String locationName, DayOfWeek dayOfWeek) {
        return getWeatherMap(locationName).get(dayOfWeek);
    }


}
