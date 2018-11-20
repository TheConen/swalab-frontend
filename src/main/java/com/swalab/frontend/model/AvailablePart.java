package com.swalab.frontend.model;

import com.swalab.frontend.api.INamedArtefact;
import com.swalab.frontend.util.IdGenerator;

public class AvailablePart implements INamedArtefact {

    private Long id = IdGenerator.getNewId();
    private String name;
    private String description;

    public AvailablePart() {
    }

    public AvailablePart(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
