package com.mahavastu.advisor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahavastu.advisor.model.Advice;
import com.mahavastu.advisor.model.LevelEnum;
import com.mahavastu.advisor.model.RequestResult;
import com.mahavastu.advisor.service.AdviceService;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://horo3.mahavastu.com:8080"})
@RequestMapping("advice")
public class AdviceController {

    @Autowired
    private AdviceService adviceService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public RequestResult advice(@RequestBody List<Advice> advices) {
        return adviceService.advice(advices);
    }

    @GetMapping("/{query-id}/{site-id}/{level}")
    public List<Advice> getAdvices(@PathVariable("query-id") Integer queryId,
                                   @PathVariable("site-id") Integer siteId,
                                   @PathVariable("level") LevelEnum level)
    {
        return adviceService.getAdvices(queryId, siteId, level);
    }
}
