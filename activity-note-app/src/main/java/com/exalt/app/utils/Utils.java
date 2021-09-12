package com.exalt.app.utils;


import com.exalt.app.controller.NoteController;
import com.exalt.app.model.Activity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.Date;

/**
 * TODO: add any common utility method here
 */
public class Utils {
    private Utils() {
    }

    static Logger log = LogManager.getLogger(NoteController.class);

    public static Date getActivityStartTime(Activity activity, DayOfWeek dayOfWeek) {
        return getStartTime(activity.getTimestamp(), dayOfWeek);
    }

    public static Date getStartTime(Date date, DayOfWeek dayOfWeek) {

        Calendar calendar = Calendar.getInstance();
        Calendar calendar1 = Calendar.getInstance();

        calendar1.setTime(date);
        int activityDay = calendar1.get(Calendar.DAY_OF_WEEK) - 1;

        int noteDay = dayOfWeek.getValue();

        if (noteDay == 7)
            noteDay = 0;


        calendar.setTime(date);

        if (activityDay - noteDay < 0) {
            int days = (noteDay - activityDay);


            calendar.add(Calendar.DATE, (days));  // number of days to add
        } else if (activityDay - noteDay > 0) {
            int days = (int) (activityDay - noteDay);

            calendar.add(Calendar.DATE, (7 - days));
        }

        return calendar.getTime();
    }

    public static DayOfWeek getDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return DayOfWeek.of(calendar.get(Calendar.DAY_OF_WEEK)).minus(1);
    }


}