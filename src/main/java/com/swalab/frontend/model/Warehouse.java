package com.swalab.frontend.model;

import com.swalab.frontend.INamedArtefact;

import java.util.List;

public class Warehouse implements INamedArtefact {
    
    private List<WarehousePartAndOrder> partAndOrders;

    public List<WarehousePartAndOrder> getPartAndOrders() {
        return partAndOrders;
    }

    public void setPartAndOrders(List<WarehousePartAndOrder> partAndOrders) {
        this.partAndOrders = partAndOrders;
    }

    public void addPartOrOrder(WarehousePartAndOrder partAndOrder) {
        partAndOrders.add(partAndOrder);
    }

    //TODO
    @Override
    public String getName() {
        return "What's the name of the warehouse?";
    }
}
