package com.swalab.backend.model;

import java.util.Date;

public class Note extends AbstractTaskAndNote {

    public Note(String title, String description, Status status, Date creationDate, Technican technican) {
        super(title, description, status, creationDate, technican);
    }
}
