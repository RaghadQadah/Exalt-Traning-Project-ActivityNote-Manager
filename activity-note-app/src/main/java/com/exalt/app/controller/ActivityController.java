package com.exalt.app.controller;

import com.exalt.app.model.Activity;
import com.exalt.app.service.activity.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class ActivityController {

    @Autowired
    ActivityService activityService;
    //creating a get mapping that retrieves all the Activity detail from the database

    @GetMapping("/activity")
    private List<Activity> getAllActivities() {
        return activityService.getAll();
    }

    //creating a get mapping that retrieves the detail of a specific Activity
    @GetMapping("/activity/{id}")
    private Activity getActivity(@PathVariable("id") Long id) {
        return activityService.getById(id);
    }

    //creating a delete mapping that deletes a specific Activity
    @DeleteMapping("/activity/{id}")
    private void deleteActivity(@PathVariable("id") Long id) {
        activityService.deleteById(id);
    }

    //creating post mapping that post the Activity detail in the database
    @PostMapping("/activity")
    private long addActivity(@RequestBody Activity activity) {
        activityService.saveOrUpdate(activity);
        return activity.getId();
    }

    @PutMapping("/activity")
    private long update(@RequestBody Activity activity) {
        activityService.saveOrUpdate(activity);
        return activity.getId();
    }

}

