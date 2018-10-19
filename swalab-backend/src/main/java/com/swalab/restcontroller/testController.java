package com.swalab.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class testController {

    @Autowired
    public testController() {
        //ToDo
    }
    
    @RequestMapping("/hello")
    public String getHello() {
        return "Hello World! " + Math.random();
    }

    
}