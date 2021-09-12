package com.exalt.app.service.activity;

import com.exalt.app.model.Activity;
import com.exalt.app.service.crud.AbstractCurdService;
import org.springframework.stereotype.Service;

@Service
public class ActivityService extends AbstractCurdService<Activity, Long> implements IActivityService {

}
