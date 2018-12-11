package com.swalab.frontend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swalab.frontend.api.INamedArtefact;
import com.swalab.frontend.util.IdGenerator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Customer implements INamedArtefact{

    private long id = IdGenerator.getNewId();
    private String name;
    private String geolocation;
    private String phone;
    private String mail;
    private String web;
    private String address;
    private List<Product> products;
    private List<Appointment> appointmentHistoryList = new ArrayList<>();

    public Customer() {
    }

    public Customer(String name, String geolocation, String phone, String mail, String web, String address, List<Product> products) {
        this.name = name;
        this.geolocation = geolocation;
        this.phone = phone;
        this.mail = mail;
        this.web = web;
        this.address = address;
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGeolocation() {
        return geolocation;
    }

    public void setGeolocation(String geolocation) {
        this.geolocation = geolocation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Product> getProducts() {
        return products;
    }

    @JsonIgnore
    public ObservableList<Product> getObservableProducts() {
        return products == null ? FXCollections.observableArrayList() : FXCollections.observableList(products);
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Appointment> getAppointmentHistoryList() {
        return appointmentHistoryList;
    }

    @JsonIgnore
    public ObservableList<Appointment> getObservableAppointmentHistoryList() {
        return FXCollections.observableList(appointmentHistoryList);
    }

    public void setAppointmentHistoryList(List<Appointment> appointmentHistoryList) {
        this.appointmentHistoryList = appointmentHistoryList;
    }

    public long getID() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
