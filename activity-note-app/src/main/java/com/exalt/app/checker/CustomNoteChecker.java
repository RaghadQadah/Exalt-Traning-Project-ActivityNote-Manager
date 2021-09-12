package com.exalt.app.checker;

import com.exalt.app.Factory.DataAdapterFactory;
import com.exalt.app.model.*;
import com.exalt.app.utils.adapter.IDataAdapter;

import javax.persistence.*;
import java.util.Date;

@Entity
public class CustomNoteChecker extends DefaultNoteChecker {

    @Embedded
    private Event event;

    /**
     * Sets the default start time if it is not updated by the handler
     *
     * @param note
     * @param activity
     * @return
     */
    @Override
    protected Date getActivityStartTime(Note note, Activity activity) {
        DataAdapterFactory dataAdapterFactory = new DataAdapterFactory();
        IDataAdapter adapter = dataAdapterFactory.getAdapter("JSOUP");
        if (adapter != null)
        {        return adapter.getStartDate(this.getEvent().getEventName(),
                note.getCondition().getLocation().getName());

        } else {
            return super
                    .getActivityStartTime(note, activity);
        }

    }


    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
