package com.mahavastu.advisor.service;

import com.mahavastu.advisor.entity.ClientEntity;
import com.mahavastu.advisor.entity.OccupationEntity;
import com.mahavastu.advisor.entity.converter.Converter;
import com.mahavastu.advisor.model.Client;
import com.mahavastu.advisor.repository.ClientRepository;
import com.mahavastu.advisor.repository.OccupationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService{

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private OccupationRepository occupationRepository;

    @Override
    public Client login(Client client) {
        if(client == null)
            return null;
        ClientEntity clientEntity = clientRepository.getById(client.getClientId());
        if(clientEntity != null && clientEntity.getPassword().equals(client.getPassword())) {
            return Converter.getClientFromClientEntity(clientEntity);
        }
        return null;
    }

    @Override
    public Client addClient(Client client) {
        OccupationEntity occupationEntity = occupationRepository.getById(client.getOccupation().getOccupationId());
        System.out.println(occupationEntity);
        ClientEntity clientEntity = Converter.getClientEntityFromClient(client, occupationEntity);
        if(clientEntity != null) {
            ClientEntity savedEntity = clientRepository.save(clientEntity);
            System.out.println("saved: " + savedEntity);
            return Converter.getClientFromClientEntity(savedEntity);
        }
        return null;
    }
}
