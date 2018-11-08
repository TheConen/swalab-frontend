package com.swalab.frontend.model;

import java.util.Date;
import java.util.List;

/**
 * This is the machine from the customer which needs a service. Don't mix up with "Part" which is a component of a product.
 */
public class Product {

    private String name;
    private String description;
    private long serialNumber;
    private Date purchaseDate;
    private String documents;
    private List<Bom> productParts;

    public Product(String name, String description, long serialNumber, Date purchaseDate, String documents, List<Bom> productParts) {
        this.name = name;
        this.description = description;
        this.serialNumber = serialNumber;
        this.purchaseDate = purchaseDate;
        this.documents = documents;
        this.productParts = productParts;
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

    public List<Bom> getProductParts() {
        return productParts;
    }

    public void setProductParts(List<Bom> productParts) {
        this.productParts = productParts;
    }
}
