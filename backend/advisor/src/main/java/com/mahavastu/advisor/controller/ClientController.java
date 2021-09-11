package com.mahavastu.advisor.controller;

import com.mahavastu.advisor.model.Client;
import com.mahavastu.advisor.model.ClientLoginDetails;
import com.mahavastu.advisor.model.Occupation;
import com.mahavastu.advisor.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {
        "http://localhost:4200", "http://horo3.mahavastu.com:8080"
})
@RequestMapping("client")
public class ClientController
{

    @Autowired
    private ClientService clientService;

    @PostMapping("/login")
    @ResponseBody
    public Client login(@RequestBody ClientLoginDetails client)
    {
        return clientService.login(client);
    }

    @PostMapping()
    @ResponseBody
    public Client addClient(@RequestBody Client client)
    {
        return clientService.addClient(client);
    }

    @PutMapping
    @ResponseBody
    public Client updateClient(@RequestBody Client client)
    {
        return clientService.updateClient(client);
    }

    @GetMapping("/occupations")
    @ResponseBody
    public List<Occupation> getAllOccupations()
    {
        return clientService.getAllOccupations();
    }
    
    @PostMapping("/forgot-password")
    @ResponseBody
    public String forgotPassword(@RequestBody Client client)
    {
        return clientService.sendClientPasswordMail(client);
    }
}
