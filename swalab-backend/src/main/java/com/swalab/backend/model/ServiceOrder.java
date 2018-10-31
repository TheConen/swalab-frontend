package com.swalab.backend.model;

import java.util.Date;
import java.util.List;

public class ServiceOrder {

    private String description;
    private Technican technican;
    private ServiceProduct serviceProduct;
    private Date plannedTime;
    private List<AbstractPartAndService> plannedPartsAndServices;
    private Date creationDate;
    private Date dateTimeFrom;
    private Date dateTimeTo;
    private Status status;
    private List<AbstractPartAndService> usedPartsAndServices;
    private Date serviceDateTime;
    private Date realDateFrom;
    private Date realDateTo;

    public ServiceOrder(String description, Technican technican, ServiceProduct serviceProduct, Date plannedTime, List<AbstractPartAndService> plannedPartsAndServices, Date creationDate, Date dateTimeFrom, Date dateTimeTo, Status status) {
        this.description = description;
        this.technican = technican;
        this.serviceProduct = serviceProduct;
        this.plannedTime = plannedTime;
        this.plannedPartsAndServices = plannedPartsAndServices;
        this.creationDate = creationDate;
        this.dateTimeFrom = dateTimeFrom;
        this.dateTimeTo = dateTimeTo;
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Technican getTechnican() {
        return technican;
    }

    public void setTechnican(Technican technican) {
        this.technican = technican;
    }

    public ServiceProduct getServiceProduct() {
        return serviceProduct;
    }

    public void setServiceProduct(ServiceProduct serviceProduct) {
        this.serviceProduct = serviceProduct;
    }

    public Date getPlannedTime() {
        return plannedTime;
    }

    public void setPlannedTime(Date plannedTime) {
        this.plannedTime = plannedTime;
    }

    public List<AbstractPartAndService> getPlannedPartsAndServices() {
        return plannedPartsAndServices;
    }

    public void setPlannedPartsAndServices(List<AbstractPartAndService> plannedPartsAndServices) {
        this.plannedPartsAndServices = plannedPartsAndServices;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getDateTimeFrom() {
        return dateTimeFrom;
    }

    public void setDateTimeFrom(Date dateTimeFrom) {
        this.dateTimeFrom = dateTimeFrom;
    }

    public Date getDateTimeTo() {
        return dateTimeTo;
    }

    public void setDateTimeTo(Date dateTimeTo) {
        this.dateTimeTo = dateTimeTo;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<AbstractPartAndService> getUsedPartsAndServices() {
        return usedPartsAndServices;
    }

    public void setUsedPartsAndServices(List<AbstractPartAndService> usedPartsAndServices) {
        this.usedPartsAndServices = usedPartsAndServices;
    }

    public Date getServiceDateTime() {
        return serviceDateTime;
    }

    public void setServiceDateTime(Date serviceDateTime) {
        this.serviceDateTime = serviceDateTime;
    }

    public Date getRealDateFrom() {
        return realDateFrom;
    }

    public void setRealDateFrom(Date realDateFrom) {
        this.realDateFrom = realDateFrom;
    }

    public Date getRealDateTo() {
        return realDateTo;
    }

    public void setRealDateTo(Date realDateTo) {
        this.realDateTo = realDateTo;
    }
}
