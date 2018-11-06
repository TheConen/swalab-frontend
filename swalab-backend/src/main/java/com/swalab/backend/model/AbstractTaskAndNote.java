package com.swalab.backend.model;

import java.util.Date;

public abstract class AbstractTaskAndNote {

    private String title;
    private String description;
    private Date creationDate;
    private Technican technican;

    public AbstractTaskAndNote(String title, String description, Date creationDate, Technican technican) {
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.technican = technican;
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

    public Technican getTechnican() {
        return technican;
    }

    public void setTechnican(Technican technican) {
        this.technican = technican;
    }
}
