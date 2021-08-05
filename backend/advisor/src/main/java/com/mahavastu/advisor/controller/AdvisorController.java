package com.mahavastu.advisor.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("advisor")
public class AdvisorController {

    @GetMapping("/health")
    public String health() {
        return "STATUS : UP";
    }
}
