package com.swalab.backend.model;

import java.util.List;

public class Customer {

    private String name;
    private String geolocation;
    private String phone;
    private String mail;
    private String web;
    private String address;
    private List<ServiceProduct> serviceProductList;
    private List<ServiceOrder> serviceHistoryList;

    public Customer(String name, String geolocation, String phone, String mail, String web, String address, List<ServiceProduct> serviceProductList, List<ServiceOrder> serviceHistoryList) {
        this.name = name;
        this.geolocation = geolocation;
        this.phone = phone;
        this.mail = mail;
        this.web = web;
        this.address = address;
        this.serviceProductList = serviceProductList;
        this.serviceHistoryList = serviceHistoryList;
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

    public List<ServiceProduct> getServiceProductList() {
        return serviceProductList;
    }

    public void setServiceProductList(List<ServiceProduct> serviceProductList) {
        this.serviceProductList = serviceProductList;
    }

    public List<ServiceOrder> getServiceHistoryList() {
        return serviceHistoryList;
    }

    public void setServiceHistoryList(List<ServiceOrder> serviceHistoryList) {
        this.serviceHistoryList = serviceHistoryList;
    }
}
