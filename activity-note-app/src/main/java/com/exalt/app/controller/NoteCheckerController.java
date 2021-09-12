package com.exalt.app.controller;

import com.exalt.app.checker.DefaultNoteChecker;
import com.exalt.app.checker.NoteChecker;
import com.exalt.app.service.checker.NoteCheckerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class NoteCheckerController {

    Logger log = LogManager.getLogger(NoteCheckerController.class);

    @Autowired
    NoteCheckerService noteCheckerService;


    @GetMapping("/notes-checkers")
    private List<NoteChecker> getAllNoteCheckers() {
        return noteCheckerService.getAll();
    }

}
