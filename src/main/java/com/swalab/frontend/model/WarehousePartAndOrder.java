package com.swalab.frontend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swalab.frontend.api.INamedArtefact;
import com.swalab.frontend.util.IdGenerator;

import java.util.Date;

public class WarehousePartAndOrder implements INamedArtefact {

    private long id = IdGenerator.getNewId();
    private long orderNumber;
    private String description;
    private Date orderDate;
    private PartWithQuantity part; // TODO specify what should be shown
    private Status status;

    public WarehousePartAndOrder() {
    }

    public WarehousePartAndOrder(long orderNumber, String description, Date orderDate, PartWithQuantity part, Status status) {
        this.orderNumber = orderNumber;
        this.description = description;
        this.orderDate = orderDate;
        this.part = part;
        this.status = status;
    }

    @Override
    @JsonIgnore
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

    public PartWithQuantity getPart() {
        return part;
    }

    public void setPart(PartWithQuantity part) {
        this.part = part;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public long getID(){
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
