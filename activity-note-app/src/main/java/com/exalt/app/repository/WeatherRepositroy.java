package com.exalt.app.repository;

import com.exalt.app.model.Weather;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WeatherRepositroy extends CrudRepository<Weather, Long> {
    List<Weather> findAllByLocationName(String locationName);
}
