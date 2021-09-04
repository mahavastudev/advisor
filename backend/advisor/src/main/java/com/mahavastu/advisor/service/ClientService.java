package com.mahavastu.advisor.service;

import com.mahavastu.advisor.model.Client;
import com.mahavastu.advisor.model.LoginDetails;
import com.mahavastu.advisor.model.Occupation;

import java.util.List;

public interface ClientService {
    Client login(LoginDetails client);

    Client addClient(Client client);

    List<Occupation> getAllOccupations();

    Client updateClient(Client client);
}
