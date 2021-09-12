package com.exalt.app.service.notification;

import com.exalt.app.model.Activity;
import com.exalt.app.model.Notification;

import java.util.List;

public interface INotificationService {
    public List<Notification> getNotifications(List<Activity> activityList);
    public Notification createNotification(Activity activity);
}
