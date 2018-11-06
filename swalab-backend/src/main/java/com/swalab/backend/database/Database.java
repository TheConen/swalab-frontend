package com.swalab.backend.database;

import com.swalab.backend.model.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Database {

    private List<Customer> customers = new ArrayList<>();
    private List<AbstractTaskAndNote> taskAndNoteList = new ArrayList<>();
    private Technican technican;

    @Autowired
    public Database() {
        initTechnican();
        initCustomers();
        initNotes();
    }

    private void initTechnican() {
        technican = new Technican("technican@web.de", "technican name", "password", "01234567");
    }

    private void initNotes() {
        taskAndNoteList.add(new Task("note title", "note description", Status.OPEN, new Date(System.currentTimeMillis()), technican));
    }

    private void initCustomers() {
        Part part = new Part("part name", "part description");
        Bom bom = new Bom(part, 42, "kg");
        List<Bom> bomList = new ArrayList<>();
        bomList.add(bom);
        ServiceProduct serviceProduct = new ServiceProduct("serviceProduct name", "description blah", 678, new Date(System.currentTimeMillis()), "documents.com", bomList);
        List<ServiceProduct> serviceProductList = new ArrayList<>();
        serviceProductList.add(serviceProduct);
        List<AbstractPartAndService> plannedPartsAndServices = new ArrayList<>();
        plannedPartsAndServices.add(part);
        ServiceOrder serviceOrder = new ServiceOrder("serviceOrder description", technican, serviceProduct, new Date(System.currentTimeMillis()), plannedPartsAndServices, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), Status.IN_PROGRESS);
        List<ServiceOrder> serviceHistoryList = new ArrayList<>();
        serviceHistoryList.add(serviceOrder);
        Customer customer = new Customer("customer name", "geolocation", "0987654321", "customer@web.de", "customerwebsite.com", "CustomerStreet 15", serviceProductList, serviceHistoryList);
        customers.add(customer);
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public List<AbstractTaskAndNote> getTaskAndNoteList() {
        return taskAndNoteList;
    }

    public Technican getTechnican() {
        return technican;
    }
}
