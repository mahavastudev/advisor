package com.mahavastu.advisor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("advisor")
public class AdvisorController {

    @GetMapping("/health")
    public String health() {
        return "STATUS : UP";
    }
}
