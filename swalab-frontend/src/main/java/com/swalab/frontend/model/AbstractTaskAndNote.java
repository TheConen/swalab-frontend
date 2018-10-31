package com.swalab.frontend.model;

import java.util.Date;

public abstract class AbstractTaskAndNote {

    private String title;
    private String description;
    private Status status;
    private Date creationDate;
    private Technican technican;

    public AbstractTaskAndNote(String title, String description, Status status, Date creationDate, Technican technican) {
        this.title = title;
        this.description = description;
        this.status = status;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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
