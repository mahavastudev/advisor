package com.mahavastu.advisor.controller;

import com.mahavastu.advisor.model.Advice;
import com.mahavastu.advisor.model.LevelEnum;
import com.mahavastu.advisor.model.RequestResult;
import com.mahavastu.advisor.service.AdviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
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
