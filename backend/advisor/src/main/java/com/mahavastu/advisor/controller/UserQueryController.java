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

    @GetMapping("/user-query-id/{user-query-id}")
    @ResponseBody
    public UserQuery getUserQueryByQueryId(@PathVariable("user-query-id") int userQueryId) {
        return userQueryService.getQueryById(userQueryId);
    }

    @GetMapping("/client/{client-id}")
    @ResponseBody
    public List<UserQuery> getUserQueriesByClientId(@PathVariable("client-id") int clientId) {

        return userQueryService.getUserQueriesByClientId(clientId);
    }

    @GetMapping("/site/{site-id}")
    @ResponseBody
    public List<UserQuery> getUserQueriesBySiteId(@PathVariable("site-id") int siteId) {

        return userQueryService.getUserQueriesBySiteId(siteId);
    }

    @PostMapping("/resolve-query/{user-query-id}")
    @ResponseBody
    public String resolveQueryByQueryId(@PathVariable("user-query-id") int userQueryId) {
        return userQueryService.resolveQueryByQueryId(userQueryId);
    }
}
