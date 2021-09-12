package com.exalt.app.checker;

import com.exalt.app.model.*;
import com.exalt.app.utils.adapter.IDataAdapter;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = DefaultNoteChecker.class, name = "DefaultNoteChecker"),
        @JsonSubTypes.Type(value = CustomNoteChecker.class, name = "CustomNoteChecker")
})
@Entity(name = "NoteChecker")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class NoteChecker extends BaseModel {

    @Transient
    private IDataAdapter adapter;

    public abstract Activity checkNoteCondition(Note note, Condition condition, Weather actualWeather);


    public IDataAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(IDataAdapter adapter) {
        this.adapter = adapter;
    }

}



