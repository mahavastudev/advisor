package com.mahavastu.advisor.controller;

import com.mahavastu.advisor.model.Client;
import com.mahavastu.advisor.model.MasterConcern;
import com.mahavastu.advisor.model.UserQuery;
import com.mahavastu.advisor.service.UserQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("user-query")
public class UserQueryController {

    @Autowired
    private UserQueryService userQueryService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserQuery addQuery(@RequestBody UserQuery userQuery) {

        return userQueryService.addUserQuery(userQuery);
    }

    @GetMapping("/{userQueryId}")
    @ResponseBody
    public UserQuery getUserQueryByQueryId(@PathVariable("userQueryId") int userQueryId) {
        return userQueryService.getQueryById(userQueryId);
    }

    @GetMapping("/client/{clientId}")
    @ResponseBody
    public List<UserQuery> getUserQueriesByClientId(@PathVariable("clientId") int clientId) {

        return userQueryService.getUserQueriesByClientId(clientId);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserQuery getDummyUserQuery() {
        return new UserQuery(1, new Client(), "Query Text", new Timestamp(Calendar.getInstance().getTimeInMillis()), new Timestamp(Calendar.getInstance().getTimeInMillis()), 1, 1, new MasterConcern(1, "DummyConcern"));
    }

}
