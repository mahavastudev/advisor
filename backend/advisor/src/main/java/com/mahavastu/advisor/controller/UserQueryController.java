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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mahavastu.advisor.model.UserQuery;
import com.mahavastu.advisor.service.UserQueryService;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://horo3.mahavastu.com:8080"})
@RequestMapping("user-query")
public class UserQueryController {

    @Autowired
    private UserQueryService userQueryService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserQuery addQuery(@RequestBody UserQuery userQuery) {

        return userQueryService.addUserQuery(userQuery);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<UserQuery> getAllQueries() {
        return userQueryService.getAllQueries();
    }

    @GetMapping("/user-query-id/{userQueryId}")
    @ResponseBody
    public UserQuery getUserQueryByQueryId(@PathVariable("userQueryId") int userQueryId) {
        return userQueryService.getQueryById(userQueryId);
    }

    @GetMapping("/client/{clientId}")
    @ResponseBody
    public List<UserQuery> getUserQueriesByClientId(@PathVariable("clientId") int clientId) {

        return userQueryService.getUserQueriesByClientId(clientId);
    }



}
