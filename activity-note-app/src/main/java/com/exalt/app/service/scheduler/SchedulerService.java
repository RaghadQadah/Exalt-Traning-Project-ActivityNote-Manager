package com.exalt.app.service.scheduler;

import com.exalt.app.checker.DefaultNoteChecker;
import com.exalt.app.checker.NoteChecker;
import com.exalt.app.model.*;
import com.exalt.app.service.activity.IActivityService;
import com.exalt.app.service.checker.INoteCheckerService;
import com.exalt.app.service.location.ILocationService;
import com.exalt.app.service.note.INoteService;
import com.exalt.app.service.weather.IWeatherService;
import com.exalt.app.utils.Utils;
import com.exalt.app.utils.adapter.Coordinates;
import com.exalt.app.utils.adapter.IDataAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * TODO: move this to service package or another location
 */

@Component
public class SchedulerService {

    private static final long SCHEDULE_TIME = 15000L;

    Logger log = LogManager.getLogger(SchedulerService.class);


    @Autowired
    INoteService noteService;

    @Autowired
    INoteCheckerService noteCheckerService;

    @Autowired
    IActivityService activityService;

    @Autowired
    IWeatherService weatherService;

    @Autowired
    ILocationService locationService;

    @Autowired
    private IDataAdapter dataAdapter;

    /**
     * Init the data
     */
    @PostConstruct
    public void init() {
        /**
         * Adds default note checker
         */
        if (noteCheckerService.getAll().isEmpty()) {
            noteCheckerService.saveOrUpdate(new DefaultNoteChecker());
        }
        /**
         * Adds default location
         */
        if (locationService.getAll().isEmpty()) {
            locationService.saveOrUpdate(Location.getDefault());
        }
    }

    @Scheduled(fixedRate = SCHEDULE_TIME)
    public void checkNotes() {

        /**
         * Updates location
         */
        for (Location location : locationService.getAll()) {
            updateLocation(location);
            /**
             * Updates weathers for one week
             */
            updateWeathers(location);
        }

        /**
         * Process notes
         */
        List<Note> noteList = noteService.getAll();
        for (Note note : noteList) {
            log.info("*handle note with note id= " + note.getId());

            NoteChecker noteChecker = note.getNoteChecker();

            /**
             * Try to get default note checker
             */
            if (noteChecker == null) {
                noteChecker = noteCheckerService.getDefaultChecker();
            }

            if (noteChecker == null) {
                log.info("**************Null note handler found for note with id= " + note.getId());
                continue;
            }

            Date currentDate = new Date();
            Condition noteCondition = note.getCondition();

            log.info("handle note with note id= " + note.getId() + " and check its condition");


            /*
             * Checks one-time activity
             */
            Weather actualWeather = null;
            if (noteCondition.getDay() != null && noteCondition.getLocation() != null) {
                actualWeather = weatherService.getWeather(noteCondition.getLocation().getName(),
                        noteCondition.getDay());

                if (actualWeather == null) {
                    log.info("Unable to get the matching weather for note " + note.getId());
                    continue;
                }
            }
            Activity activity = null;

            if (note.getStateEnabled()) {
                noteChecker.setAdapter(dataAdapter);
                if (note.getPeriod() == null || note.getPeriod() == 0) {
                    activity = noteChecker.checkNoteCondition(note, noteCondition, actualWeather);
                    note.setStateEnabled(false);
                    noteService.saveOrUpdate(note);
                    if (activity != null) {
                        activity.setNote(note);
                        activityService.saveOrUpdate(activity);
                    }
                    log.info("Note will be used only one time then the state will become disabled");
                } else if (currentDate.getTime() - note.getLastTimeProcessing().getTime() > note.getPeriod()) {
                    activity = noteChecker.checkNoteCondition(note, noteCondition, actualWeather);
                    noteService.saveOrUpdate(note);
                    if (activity != null) {
                        activity.setNote(note);
                        activityService.saveOrUpdate(activity);
                    }
                } else {
                    log.info(note.getId() + " Cant use this note now");
                }
            }
            if (activity != null) {
                activity.setNote(note);
                activityService.saveOrUpdate(activity);
                noteService.saveOrUpdate(note);

            } else {
                log.info("not matched");
            }
        }


    }

    private void updateWeathers(Location location) {
        Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        Map<DayOfWeek, Weather> weathersMap = weatherService.getWeatherMap(location.getName());

        /**
         * Loop through all days in the week
         */
        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            Weather actualWeather = dataAdapter.getWeatherDetails(location.getName(), dayOfWeek);
            Weather weatherFromDataBase = weathersMap.get(dayOfWeek);

            /**
             * Weather item is already in the table, just updating weather details
             */
            if (weatherFromDataBase != null) {
                weatherFromDataBase.setWind(actualWeather.getPrecipitation());
                weatherFromDataBase.setPrecipitation(actualWeather.getPrecipitation());
                weatherFromDataBase.setTemperature(actualWeather.getTemperature());
                weatherFromDataBase.setTimestamp(Utils.getStartTime(new Date(), dayOfWeek));
                weatherFromDataBase.setLocation(location);
                weatherService.saveOrUpdate(weatherFromDataBase);
            } else {
                actualWeather.setLocation(location);
                actualWeather.setTimestamp(Utils.getStartTime(new Date(), dayOfWeek));
                weatherService.saveOrUpdate(actualWeather);
            }

        }

    }

    private void updateLocation(Location location) {

        /**
         * Updates the coordinates of location if the data is not there.
         */
        if (location.getLatitude() == null || location.getLongitude() == null) {
            Coordinates coordinates = dataAdapter.getCoordinates(location.getName());

            location.setLatitude(coordinates.getLatitude());
            location.setLongitude(coordinates.getLongitude());

            //add this location to dataBase
            locationService.saveOrUpdate(location);
        }

    }

}
