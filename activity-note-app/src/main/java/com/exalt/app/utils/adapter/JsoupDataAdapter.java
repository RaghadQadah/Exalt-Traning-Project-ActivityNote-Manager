package com.exalt.app.utils.adapter;

import com.exalt.app.model.Weather;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The jsoup data adapter used to scrape the data from google.com
 */
public class JsoupDataAdapter implements IDataAdapter {

    static Logger log = LogManager.getLogger(JsoupDataAdapter.class);
    static String coordinatesClassName = "Z0LcW XcVN5d";
    static String eventNameClassName = "bVj5Zb FozYP";
    static String eventDateClassName = "t3gkGd";

    @Override
    public Coordinates getCoordinates(String locationName) {

        try {
            Document doc = getJsoup(locationName + "+longitude+and+latitude").get();
            /**
             * TODO: try to get more general selector.
             */
            Elements result = doc.getElementsByClass(coordinatesClassName);
            String lat = "0";
            String Lon = "0";
            Coordinates coordinates = new Coordinates();
            try {
                String[] resultString = (result.text()).split(",");
                lat = resultString[0];
                Lon = resultString[1];
            } catch (Exception e) {

            }

            String pattern = "(\\d+).\\d+";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(lat);

            if (m.find()) {
                coordinates.setLatitude(m.group(0));
            }

            Matcher m1 = r.matcher(Lon);
            if (m1.find()) {
                coordinates.setLongitude(m1.group(0));

            }

            return coordinates;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public Weather getWeatherDetails(String locationName, DayOfWeek dayOfWeek) {
        /**
         * Add jsoup impl here
         */


        try {
            Document doc = getJsoup("weather+in+" + locationName + "+" + dayOfWeek.name()).get();

            Element temperature = doc.getElementById("wob_tm");
            Element precipitation = doc.getElementById("wob_pp");
            Element wind = doc.getElementById("wob_ws");

            Weather weather = new Weather();
            weather.setTemperature(Integer.valueOf(temperature.text()));
            weather.setPrecipitation(precipitation.text());
            weather.setWind(wind.text());


            return weather;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    @Override
    public Date getStartDate(String eventName, String locationName) {
        log.info("Event Name: " + eventName);
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        try {
            Document doc = getJsoup("events+in" + locationName).get();
            String dateTime = "";
            Elements Names = doc.getElementsByClass(eventNameClassName);
            Elements times = doc.getElementsByClass(eventDateClassName);
            int i = 0;
            int j = 0;
            for (Element name : Names) {
                i++;
                if (name.text().startsWith(eventName)) {

                    dateTime = "";
                    for (Element time : times) {
                        j++;


                        if (i == j) {

                            System.out.println(name.text());
                            System.out.println(time.text());
                            dateTime = time.text();
                        }

                    }
                }
            }

            List<SimpleDateFormat> patterns = new ArrayList<SimpleDateFormat>();
            patterns.add(new SimpleDateFormat("EEE, d MMM HH:mm"));
            patterns.add(new SimpleDateFormat("EEE, d MMM HH:mm a"));
            patterns.add(new SimpleDateFormat("EEE, MMM d HH:mm"));
            patterns.add(new SimpleDateFormat("EEE, MMM d HH:mm a"));
            patterns.add(new SimpleDateFormat("EEE, d MMM"));
            patterns.add(new SimpleDateFormat("EEE, MMM d"));


            for (SimpleDateFormat pattern : patterns) {
                try {
                    date = pattern.parse(dateTime);
                    calendar.setTime(date);
                    calendar.set(Calendar.YEAR, 2021);
                    return calendar.getTime();
                } catch (ParseException e) {
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Connection getJsoup(String query) {
        return Jsoup.connect("https://www.google.com/search?q=q" + query)
                .header("accept-language", "en-US,en;q=0.9,ar;q=0.8").header("Content-Language", "en-US");
    }


}