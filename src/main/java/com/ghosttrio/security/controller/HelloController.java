package com.ghosttrio.security.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    @CrossOrigin("http://localhost:8080")
    public String hello() {
        return "Hello!";
    }
}
