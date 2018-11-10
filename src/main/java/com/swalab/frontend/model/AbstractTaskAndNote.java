package com.swalab.frontend.model;

import com.swalab.frontend.api.INamedArtefact;

import java.util.Date;

public abstract class AbstractTaskAndNote implements INamedArtefact {

    private Long id;
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


    /**
     * TODO: delete
     * default implementation for easier handling in the ui. some subtypes may override this method for returning the correct status
     * @return the status of the object
     */
    public Status getStatus(){return null;}

}
