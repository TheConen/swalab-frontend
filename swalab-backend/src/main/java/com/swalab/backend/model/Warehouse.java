package com.swalab.backend.model;

import java.util.List;

public class Warehouse {
    
    private List<WarehouseOrder> parts;

    public List<WarehouseOrder> getParts() {
        return parts;
    }

    public void setParts(List<WarehouseOrder> parts) {
        this.parts = parts;
    }
}
