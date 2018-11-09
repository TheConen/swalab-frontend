package com.swalab.frontend.model;

import com.swalab.frontend.api.INamedArtefact;

import java.util.Date;

public class WarehousePartAndOrder implements INamedArtefact {

    private Long id;
    private long orderNumber;
    private String description;
    private Date orderDate;
    private Bom part;
    private Status status;

    public WarehousePartAndOrder() {
    }

    public WarehousePartAndOrder(long orderNumber, String description, Date orderDate, Bom part, Status status) {
        this.orderNumber = orderNumber;
        this.description = description;
        this.orderDate = orderDate;
        this.part = part;
        this.status = status;
    }

    @Override
    public String getName() {
        return part.getAvailablePart().getName();
    }

    public long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Bom getPart() {
        return part;
    }

    public void setPart(Bom part) {
        this.part = part;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
