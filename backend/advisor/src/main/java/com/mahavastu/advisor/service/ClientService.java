package com.mahavastu.advisor.service;

import com.mahavastu.advisor.model.Client;

public interface ClientService {
    Client login(Client client);

    Client addClient(Client client);
}
