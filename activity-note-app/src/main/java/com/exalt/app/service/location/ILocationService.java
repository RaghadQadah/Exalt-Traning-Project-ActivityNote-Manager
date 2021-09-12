package com.exalt.app.service.location;

import com.exalt.app.model.Location;
import com.exalt.app.service.crud.ICurdService;

public interface ILocationService extends ICurdService<Location, Long> {
    public Location findLocationByName(String name);
}
