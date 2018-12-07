package com.swalab.frontend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.swalab.frontend.api.IObjectDataSourceArtefact;
import com.swalab.frontend.util.IdGenerator;

import java.util.Comparator;
import java.util.Date;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Note.class, name = "note"),
        @JsonSubTypes.Type(value = Task.class, name = "task")
})
public abstract class AbstractTaskAndNote implements IObjectDataSourceArtefact {

    private long id = IdGenerator.getNewId();
    private String title;
    private String description;
    private Date creationDate;

    public AbstractTaskAndNote(String title, String description, Date creationDate) {
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
    }

    public AbstractTaskAndNote() {
    }

    @Override
    public Comparator<AbstractTaskAndNote> getComparator(){
        return (Comparator<AbstractTaskAndNote>)(o1,o2)->o1.getCreationDate().compareTo(o2.getCreationDate());
    }

    @Override
    @JsonIgnore
    public String getName() {
        return getTitle();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public long getID(){
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
