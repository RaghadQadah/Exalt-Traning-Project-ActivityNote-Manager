package com.exalt.app.service.notification;

import com.exalt.app.model.Activity;
import com.exalt.app.model.Notification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class NotificationService implements INotificationService {

    Logger log = LogManager.getLogger(NotificationService.class);

    @Override
    public List<Notification> getNotifications(List<Activity> activityList) {

        List<Notification> notifications = new ArrayList<>();
        //check the activities from the database and create notifications
        for (Activity activity : activityList) {
            Notification notification = createNotification(activity);
            if (notification != null) {
                notifications.add(notification);
            }
        }

        return notifications;
    }

    @Override
    public Notification createNotification(Activity activity) {

        Notification notification = null;
        Date startDate = activity.getStartTime();

        // No notification without start time
        if (startDate != null) {

            long startTime = startDate.getTime();
            long currentTime = System.currentTimeMillis();

            /**
             * If the remainingTime (delta) is between (+ActivityremindBefore and -ActivityremindBefore)
             */
            long remainingTime = Math.abs(currentTime - startTime);
            log.info(activity.getId() + " activity remainingTime" + remainingTime);

            if (remainingTime <= activity.getNote().getActivityremindBefore()) {
                notification = new Notification();

                long minutes = TimeUnit.MILLISECONDS.toMinutes(remainingTime);
                long seconds = (TimeUnit.MILLISECONDS.toSeconds(remainingTime) % 60);

                if (currentTime > startTime) {
                    notification.setNotificationText("the activity " + activity.getId() + " is started from about " + minutes +
                            " minutes and " + seconds + " seconds ago.");
                } else {
                    notification.setNotificationText("the activity " + activity.getId() + " will start in about " + minutes +
                            " minutes and " + seconds + " seconds.");
                }

                notification.setActivity(activity);

                log.info("create notification for activity (Activity id=" + activity.getId() + ")");
            } else if (remainingTime < 0) {
                log.info("x notification x");
            }
        }

        return notification;
    }
}
