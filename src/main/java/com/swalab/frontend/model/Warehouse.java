package com.swalab.frontend.model;

import com.swalab.frontend.INamedArtefact;

import java.util.List;

public class Warehouse implements INamedArtefact {


    private List<WarehouseOrder> parts;

    public List<WarehouseOrder> getParts() {
        return parts;
    }

    public void setParts(List<WarehouseOrder> parts) {
        this.parts = parts;
    }

    @Override
    public String getName() {
        return "What's the name of the warehouse?";
    }
}
