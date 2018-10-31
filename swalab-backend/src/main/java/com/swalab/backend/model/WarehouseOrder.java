package com.swalab.backend.model;

import java.util.Date;
import java.util.List;

public class WarehouseOrder {

    private long orderNumber;
    private String description;
    private Date orderDate;
    private List<Bom> parts;
    private Status status;

    public WarehouseOrder(long orderNumber, String description, Date orderDate, List<Bom> parts, Status status) {
        this.orderNumber = orderNumber;
        this.description = description;
        this.orderDate = orderDate;
        this.parts = parts;
        this.status = status;
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

    public List<Bom> getParts() {
        return parts;
    }

    public void setParts(List<Bom> parts) {
        this.parts = parts;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
