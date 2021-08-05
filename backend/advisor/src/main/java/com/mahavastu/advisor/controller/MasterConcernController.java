package com.mahavastu.advisor.controller;

import com.mahavastu.advisor.model.MasterConcern;
import com.mahavastu.advisor.service.MasterConcernService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("master-concerns")
public class MasterConcernController {

    @Autowired
    private MasterConcernService masterConcernService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MasterConcern> getAllMasterConcerns() {
        return masterConcernService.getAllMasterConcerns();
    }

}
