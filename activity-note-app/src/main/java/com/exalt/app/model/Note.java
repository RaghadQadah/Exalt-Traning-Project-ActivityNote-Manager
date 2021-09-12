package com.exalt.app.model;

import com.exalt.app.checker.NoteChecker;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;


//mark class as an Entity
@Entity
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"},
        allowGetters = true)
public class Note extends BaseModel {

    private String name;
    private String description;
    private Long period;
    private Boolean stateEnabled = Boolean.TRUE;


    @OneToOne(cascade = CascadeType.ALL)
    private NoteChecker noteChecker;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date lastTimeProcessing;

    @OneToOne
    private Condition condition;

    private int activityDuration;

    /**
     * TODO: rename this to activityReminderTime
     */
    private int activityremindBefore;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Date getLastTimeProcessing() {
        return lastTimeProcessing;
    }

    public void setLastTimeProcessing(Date lastTimeProcessing) {
        this.lastTimeProcessing = lastTimeProcessing;
    }

    public Long getPeriod() {
        return period;
    }

    public void setPeriod(Long period) {
        this.period = period;
    }

    public Boolean getStateEnabled() {
        return stateEnabled;
    }

    public void setStateEnabled(Boolean stateEnabled) {
        this.stateEnabled = stateEnabled;
    }

    public int getActivityDuration() {
        return activityDuration;
    }

    public void setActivityDuration(int activityDuration) {
        this.activityDuration = activityDuration;
    }

    public int getActivityremindBefore() {
        return activityremindBefore;
    }

    public void setActivityremindBefore(int activityremindBefore) {
        this.activityremindBefore = activityremindBefore;
    }

    public NoteChecker getNoteChecker() {
        return noteChecker;
    }

    public void setNoteChecker(NoteChecker noteChecker) {
        this.noteChecker = noteChecker;
    }


}
