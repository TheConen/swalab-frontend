package com.swalab.frontend.model;

import java.util.List;

public class Technican {

    private String email;
    private String name;
    private String password;
    private String phone;
    private List<Appointment> appointments;
    private List<AbstractTaskAndNote> taskAndNotes;
    private List<Customer> customers;
    private Warehouse parts;

    public Technican(String email, String name, String password, String phone) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public List<AbstractTaskAndNote> getTaskAndNotes() {
        return taskAndNotes;
    }

    public void setTaskAndNotes(List<AbstractTaskAndNote> taskAndNotes) {
        this.taskAndNotes = taskAndNotes;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public Warehouse getParts() {
        return parts;
    }

    public void setParts(Warehouse parts) {
        this.parts = parts;
    }

}
