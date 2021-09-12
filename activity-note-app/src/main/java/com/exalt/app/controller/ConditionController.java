package com.exalt.app.controller;
import com.exalt.app.model.Condition;
import com.exalt.app.service.condition.ConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@RestController
public class ConditionController {

    @Autowired
    ConditionService conditionService;
    //creating a get mapping that retrieves all the condition detail from the database

    @GetMapping("/condition")
    private List<Condition> getAllCondition() {
        return conditionService.getAll();
    }

    //creating a get mapping that retrieves the detail of a specific condition
    @GetMapping("/condition/{id}")
    private Condition getCondition(@PathVariable("id") Long id) {
        return conditionService.getById(id);
    }

    //creating a delete mapping that deletes a specific condition
    @DeleteMapping("/condition/{id}")
    private void deleteCondition(@PathVariable("id") Long id) {
        conditionService.deleteById(id);
    }

    //creating post mapping that post the condition detail in the database
    @PostMapping("/condition")
    private long addCondition(@RequestBody Condition condition) {
        conditionService.saveOrUpdate(condition);
        return condition.getId();
    }

    @PutMapping("/condition")
    private long update(@RequestBody Condition condition) {
        conditionService.saveOrUpdate(condition);
        return condition.getId();
    }



}
