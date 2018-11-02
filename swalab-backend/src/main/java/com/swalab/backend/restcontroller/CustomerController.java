package com.swalab.backend.restcontroller;

import com.swalab.backend.database.Database;
import com.swalab.backend.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerController {

    private Database database;

    @Autowired
    public CustomerController(Database database) {
        this.database = database;
    }

    @RequestMapping("/customer/all")
    public List<Customer> getHello() {
        return database.getCustomers();
    }
}
