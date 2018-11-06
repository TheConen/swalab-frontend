package com.swalab.backend.model;

import java.util.Date;

public class Note extends AbstractTaskAndNote {

    public Note(String title, String description, Date creationDate, Technican technican) {
        super(title, description, creationDate, technican);
    }
}
