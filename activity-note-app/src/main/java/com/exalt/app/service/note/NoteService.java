package com.exalt.app.service.note;

import com.exalt.app.model.Note;
import com.exalt.app.service.crud.AbstractCurdService;
import org.springframework.stereotype.Service;

@Service
public class NoteService extends AbstractCurdService<Note, Long> implements INoteService {

}
