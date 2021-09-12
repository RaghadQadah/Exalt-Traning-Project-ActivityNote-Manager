package com.exalt.app.repository;

import com.exalt.app.model.Location;
import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<Location, Long> {

    Location findLocationByName(String name);

}
