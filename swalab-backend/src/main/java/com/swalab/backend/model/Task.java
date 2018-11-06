package com.swalab.backend.model;

import java.util.Date;

public class Task extends AbstractTaskAndNote {

    private Status status;

    public Task(String title, String description, Status status, Date creationDate, Technican technican) {
        super(title, description, creationDate, technican);
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
