package com.exalt.app.controller;
import com.exalt.app.model.Location;
import com.exalt.app.service.location.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@RestController
public class LocationController {
    @Autowired
    LocationService locationService;
    //creating a get mapping that retrieves all the location detail from the database

    @GetMapping("/location")
    private List<Location> getAllLocation() {

        return locationService.getAll();
    }

    //creating a get mapping that retrieves the detail of a specific location
    @GetMapping("/location/{id}")
    private Location getLocation(@PathVariable("id") Long id) {

        return locationService.getById(id);
    }

    //creating a delete mapping that deletes a specific location
    @DeleteMapping("/location/{id}")
    private void deleteLocation(@PathVariable("id") Long id) {

        locationService.deleteById(id);
    }

    //creating post mapping that post the location detail in the database
    @PostMapping("/location")
    private long addLocation(@RequestBody Location location) {
        locationService.saveOrUpdate(location);
        return location.getId();
    }

    @PutMapping("/location")
    private long update(@RequestBody Location location) {
        locationService.saveOrUpdate(location);
        return location.getId();
    }

    @GetMapping("/location/{name}")
    private Location findLocationByName(@PathVariable("name") String name) {

        return locationService.findLocationByName(name);
    }

}
