package com.mahavastu.advisor.controller;

import com.mahavastu.advisor.model.Client;
import com.mahavastu.advisor.model.Occupation;
import com.mahavastu.advisor.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
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

    @GetMapping("/occupations")
    @ResponseBody
    public List<Occupation> getAllOccupations() {
        return clientService.getAllOccupations();
    }
}
