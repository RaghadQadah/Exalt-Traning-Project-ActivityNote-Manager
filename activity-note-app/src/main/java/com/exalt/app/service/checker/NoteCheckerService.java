package com.exalt.app.service.checker;

import com.exalt.app.checker.NoteChecker;
import com.exalt.app.checker.DefaultNoteChecker;
import com.exalt.app.repository.NoteCheckerRepository;
import com.exalt.app.service.crud.AbstractCurdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class NoteCheckerService extends AbstractCurdService<NoteChecker, Long> implements INoteCheckerService {

    @Autowired
    private NoteCheckerRepository noteCheckerRepository;

    @Override
    public NoteChecker getDefaultChecker() {
        return getAll().stream().filter(checker -> checker instanceof DefaultNoteChecker).findAny().orElseGet(null);
    }

}
