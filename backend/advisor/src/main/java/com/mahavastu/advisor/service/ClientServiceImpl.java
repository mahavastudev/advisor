package com.mahavastu.advisor.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mahavastu.advisor.entity.ClientEntity;
import com.mahavastu.advisor.entity.ClientImageMasterEntity;
import com.mahavastu.advisor.entity.OccupationEntity;
import com.mahavastu.advisor.entity.converter.Converter;
import com.mahavastu.advisor.model.Client;
import com.mahavastu.advisor.model.Occupation;
import com.mahavastu.advisor.repository.ClientImageMasterRepository;
import com.mahavastu.advisor.repository.ClientRepository;
import com.mahavastu.advisor.repository.OccupationRepository;

@Service
public class ClientServiceImpl implements ClientService
{

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private OccupationRepository occupationRepository;

    @Autowired
    private ClientImageMasterRepository clientImageMasterRepository;

    @Override
    public Client login(Client client)
    {
        if (client == null)
            return null;
        ClientEntity clientEntity = clientRepository.getById(client.getClientId());
        if (clientEntity != null && clientEntity.getPassword().equals(client.getPassword()))
        {
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
            if(StringUtils.isEmpty(clientEntity.getPassword()))
            {
                Optional<ClientEntity> existingEntityOptional = clientRepository.findById(clientEntity.getClientId());
                if(existingEntityOptional.isPresent())
                {
                    clientEntity.setPassword(existingEntityOptional.get().getPassword());
                }
                
            }
            ClientEntity savedEntity = clientRepository.save(clientEntity);
            System.out.println("saved: " + savedEntity);
            return Converter.getClientFromClientEntity(savedEntity);
        }
        return null;
    }

    @Override
    public List<Occupation> getAllOccupations()
    {
        List<OccupationEntity> occupationEntities = occupationRepository.findAll();
        return Converter.getOccupationsFromOccupationEntities(occupationEntities);
    }
}
