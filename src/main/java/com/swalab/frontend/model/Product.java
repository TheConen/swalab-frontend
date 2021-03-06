package com.swalab.frontend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swalab.frontend.api.INamedArtefact;
import com.swalab.frontend.util.IdGenerator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Date;
import java.util.List;

/**
 * This is the machine from the customer which needs a service. Don't mix up with "Part" which is a component of a product.
 */
public class Product implements INamedArtefact {

    private long id = IdGenerator.getNewId();
    private String name;
    private String description;
    private long serialNumber;
    private Date purchaseDate;
    private String documents;
    private List<PartWithQuantity> productParts;

    public Product() {
    }

    public Product(String name, String description, long serialNumber, Date purchaseDate, String documents, List<PartWithQuantity> productParts) {
        this.name = name;
        this.description = description;
        this.serialNumber = serialNumber;
        this.purchaseDate = purchaseDate;
        this.documents = documents;
        this.productParts = productParts;
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

    public List<PartWithQuantity> getProductParts() {
        return productParts;
    }

    @JsonIgnore
    public ObservableList<PartWithQuantity> getObservableProductParts() {
        return FXCollections.observableList(productParts);
    }

    public void setProductParts(List<PartWithQuantity> productParts) {
        this.productParts = productParts;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
