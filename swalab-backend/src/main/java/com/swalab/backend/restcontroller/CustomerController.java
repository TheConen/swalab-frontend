package com.swalab.backend.restcontroller;

import com.swalab.backend.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class CustomerController {

    private List<Customer> customers = new ArrayList<>();

    @Autowired
    public CustomerController() {
        Bom bom = new Bom(new Part("part name", "part description"), 42, "kg");
        List<Bom> bomList = new ArrayList<>();
        bomList.add(bom);
        ServiceProduct serviceProduct = new ServiceProduct("serviceProduct name", "description blah", 678, new Date(System.currentTimeMillis()), "documents.com", bomList);
        List<ServiceProduct> serviceProductList = new ArrayList<>();
        serviceProductList.add(serviceProduct);
        List<ServiceOrder> serviceHistoryList = new ArrayList<>();
        Customer customer = new Customer("customer name", "geolocation", "0987654321", "customer@web.de", "customerwebsite.com", "CustomerStreet 15", serviceProductList, serviceHistoryList);
        customers.add(customer);
    }

    @RequestMapping("/customer/all")
    public List<Customer> getHello() {
        return customers;
    }
}
