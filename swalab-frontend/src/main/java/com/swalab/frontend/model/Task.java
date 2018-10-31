package com.swalab.frontend.model;

import java.util.Date;

public class Task extends AbstractTaskAndNote {

    public Task(String title, String description, Status status, Date creationDate, Technican technican) {
        super(title, description, status, creationDate, technican);
    }
}
