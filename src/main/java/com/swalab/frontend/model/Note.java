package com.swalab.frontend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class Note extends AbstractTaskAndNote {

    public Note() {
        super();
    }

    public Note(String title, String description, Date creationDate) {
        super(title, description, creationDate);
    }

    @JsonIgnore
    public Status getStatus(){return null;}
}
