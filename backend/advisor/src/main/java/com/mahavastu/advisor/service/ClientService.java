package com.mahavastu.advisor.service;

import com.mahavastu.advisor.model.Client;
import com.mahavastu.advisor.model.ClientLoginDetails;
import com.mahavastu.advisor.model.Occupation;

import java.util.List;

public interface ClientService {
    Client login(ClientLoginDetails client);

    Client addClient(Client client);

    List<Occupation> getAllOccupations();

    Client updateClient(Client client);
}
