package com.exalt.app.model;

public enum WeatherMode {
    RAINY,
    WARM,
    SUNNY,
    CLOUDY,
    WINDY;

    public static WeatherMode matchFrom(Weather weather) {

        // return new enum of unknown
        if(weather == null)
        {
            return null;
        }
        /**
         * TODO: use precipitation, wind for matching the weather mode.
         */
        if (weather.getTemperature() > 29) {
            return WARM;
        } else if (weather.getTemperature() > 25) {
            return SUNNY;
        } else if (weather.getTemperature() >= 15 && weather.getTemperature() <= 25) {
            return WINDY;
        } else if (weather.getTemperature() >= 10 && weather.getTemperature() <= 15) {
            return CLOUDY;
        } else
            return RAINY;
    }
}
