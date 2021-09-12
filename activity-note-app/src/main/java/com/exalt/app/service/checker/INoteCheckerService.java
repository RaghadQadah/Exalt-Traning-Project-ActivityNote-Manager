package com.exalt.app.service.checker;

import com.exalt.app.checker.NoteChecker;
import com.exalt.app.service.crud.ICurdService;

public interface INoteCheckerService extends ICurdService<NoteChecker, Long> {

    public NoteChecker getDefaultChecker();
}
