package com.exalt.app.controller;

import com.exalt.app.model.Notification;
import com.exalt.app.service.activity.IActivityService;
import com.exalt.app.service.notification.INotificationService;
import com.exalt.app.service.scheduler.SchedulerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class NotificationController {

    Logger log = LogManager.getLogger(SchedulerService.class);

    @Autowired
    private IActivityService activityService;

    @Autowired
    private INotificationService notificationService;

    @GetMapping("/notifications")
    private List<Notification> getNotifications() {
        return notificationService.getNotifications(activityService.getAll());
    }

}