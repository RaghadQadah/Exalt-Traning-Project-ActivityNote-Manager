package com.exalt.app.controller;


import com.exalt.app.model.Condition;
import com.exalt.app.model.Location;
import com.exalt.app.model.Note;
import com.exalt.app.checker.NoteChecker;
import com.exalt.app.service.checker.NoteCheckerService;
import com.exalt.app.service.condition.ConditionService;
import com.exalt.app.service.location.LocationService;
import com.exalt.app.service.note.NoteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.*;


@RestController
public class NoteController {

    Logger log = LogManager.getLogger(NoteController.class);

    @Autowired
    NoteService noteService;

    @Autowired
    NoteCheckerService noteCheckerService;

    //creating a get mapping that retrieves all the students detail from the database
    @Autowired
    LocationService locationService;
    @Autowired
    ConditionService conditionService;


    @GetMapping("/notes")
    private List<Note> getAllNote() {

        return noteService.getAll();
    }

    //creating a get mapping that retrieves the detail of a specific note
    @GetMapping("/note/{id}")
    private Note getNote(@PathVariable("id") Long id) {
        return noteService.getById(id);
    }

    //creating a delete mapping that deletes a specific Note
    @DeleteMapping("/note/{id}")
    private void deleteNote(@PathVariable("id") Long id) {

        noteService.deleteById(id);
    }

    //creating post mapping that post the Note detail in the database
    @PostMapping("/note")
    private long addNote(@RequestBody Note noteRequest) throws Exception {


        Note noteDb = new Note();
        noteDb.setName(noteRequest.getName());
        noteDb.setStateEnabled(noteRequest.getStateEnabled());
        noteDb.setPeriod(noteRequest.getPeriod());
        noteDb.setLastTimeProcessing(noteRequest.getLastTimeProcessing());


        noteDb.setDescription(noteRequest.getDescription());
        noteDb.setActivityDuration(noteRequest.getActivityDuration());
        noteDb.setActivityremindBefore(noteRequest.getActivityremindBefore());

        NoteChecker noteCheckerFromRequest = noteRequest.getNoteChecker();
        if (noteCheckerFromRequest != null) {

            noteDb.setNoteChecker(noteCheckerFromRequest);

            //noteDb.setNoteChecker(noteCheckerFromDb);
        }

        if (noteRequest.getCondition() == null) {
            throw new Exception("Condition not Exist");
        } else {

            Location location = locationService.findLocationByName(noteRequest.getCondition().getLocation().getName());

            if (location == null) {
                throw new Exception("Location not Exist");
            }

            log.info("Added note with location name: " + location.getName());

            Condition condition = new Condition();
            condition.setLocation(location);
            condition.setWeatherMode(noteRequest.getCondition().getWeatherMode());
            condition.setDay(noteRequest.getCondition().getDay());
            conditionService.saveOrUpdate(condition);
            noteDb.setCondition(condition);
            noteService.saveOrUpdate(noteDb);
        }

        return noteDb.getId();
    }

    @PutMapping("/note")
    private long updateNote(@RequestBody Note note) {
        //note.setDay(note.getDay());
        noteService.saveOrUpdate(note);
        return note.getId();
    }


}
