package com.exalt.app.checker;

import com.exalt.app.model.*;
import com.exalt.app.utils.Utils;

import javax.persistence.Entity;
import java.time.DayOfWeek;
import java.util.Date;

@Entity
public class DefaultNoteChecker extends NoteChecker {

    @Override
    public Activity checkNoteCondition(Note note, Condition condition, Weather actualWeather) {

        if (condition.getWeatherMode() == null ||
                (condition.getDay() == null || condition.getLocation() == null) ||
                (WeatherMode.matchFrom(actualWeather) == condition.getWeatherMode())) {

            Activity activity = new Activity();
            activity.setTimestamp(new Date());


            Date startTime = getActivityStartTime(note, activity);
            if (startTime != null) {
                activity.setStartTime(startTime);

                activity.setDuration(note.getActivityDuration());
                activity.setRemindBefore(note.getActivityremindBefore());
                return activity;
            }
        }
        return null;
    }

    /**
     * Sets the default start time if it is not updated by the handler
     *
     * @param note
     * @param activity
     * @return
     */
    protected Date getActivityStartTime(Note note, Activity activity) {
        return Utils.getActivityStartTime(activity,
                DayOfWeek.valueOf(note.getCondition().getDay().name()));
    }

}
