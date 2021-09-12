package com.exalt.app.service.location;

import com.exalt.app.model.Location;
import com.exalt.app.repository.LocationRepository;
import com.exalt.app.service.crud.AbstractCurdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService extends AbstractCurdService<Location, Long> implements ILocationService {

    @Autowired
    LocationRepository locationRepository;


    @Override
    public Location saveOrUpdate(Location location) {

        return locationRepository.save(location);
    }

    @Override
    public Location findLocationByName(String name) {
        return locationRepository.findLocationByName(name);
    }


}
