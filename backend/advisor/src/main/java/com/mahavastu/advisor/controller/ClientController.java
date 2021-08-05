package com.mahavastu.advisor.controller;

import com.mahavastu.advisor.model.Client;
import com.mahavastu.advisor.model.Site;
import com.mahavastu.advisor.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/login")
    @ResponseBody
    public Client login(@RequestBody Client client) {
        return clientService.login(client);
    }

    @PostMapping()
    @ResponseBody
    public Client addClient(@RequestBody Client client) {
        return clientService.addClient(client);
    }

}
