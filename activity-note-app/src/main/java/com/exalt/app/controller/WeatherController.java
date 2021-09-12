package com.exalt.app.controller;

import com.exalt.app.model.Weather;
import com.exalt.app.service.weather.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class WeatherController {
    @Autowired
    WeatherService weatherService;


    @GetMapping("/weather")
    private List<Weather> getAllWeather() {

        return weatherService.getAllWeather();
    }


    //creating post mapping that post the Weather detail in the database
    @PostMapping("/weather/{city}")
    private void addWeather(@PathVariable("city") String city) {
        weatherService.addWeather(city);
    }


}
