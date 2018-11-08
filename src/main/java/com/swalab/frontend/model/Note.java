package com.swalab.frontend.model;

import java.util.Date;

public class Note extends AbstractTaskAndNote {

    public Note(String title, String description, Date creationDate) {
        super(title, description, creationDate);
    }
}
