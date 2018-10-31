package com.swalab.frontend.model;

import java.util.Date;
import java.util.List;

public class ServiceProduct {

    private String name;
    private String description;
    private Customer customer;
    private long serialNumber;
    private Date purchaseDate;
    private String documents;
    private List<Bom> bom;

    public ServiceProduct(String name, String description, Customer customer, long serialNumber, Date purchaseDate, String documents, List<Bom> bom) {
        this.name = name;
        this.description = description;
        this.customer = customer;
        this.serialNumber = serialNumber;
        this.purchaseDate = purchaseDate;
        this.documents = documents;
        this.bom = bom;
    }

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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public long getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(long serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getDocuments() {
        return documents;
    }

    public void setDocuments(String documents) {
        this.documents = documents;
    }

    public List<Bom> getBom() {
        return bom;
    }

    public void setBom(List<Bom> bom) {
        this.bom = bom;
    }
}
