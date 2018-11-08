package com.swalab.frontend.model;

import java.util.Date;

public class Task extends AbstractTaskAndNote {

    private Status status;

    public Task(String title, String description, Status status, Date creationDate) {
        super(title, description, creationDate);
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
