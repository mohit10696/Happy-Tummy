package com.happytummy.happytummybackend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/greet")
public class greet {

    @RequestMapping()
    public String greet() {
        return "Hello World!";
    }
}
