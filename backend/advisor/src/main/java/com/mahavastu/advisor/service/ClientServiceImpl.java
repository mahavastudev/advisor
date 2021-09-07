package com.mahavastu.advisor.service;

import java.sql.Timestamp;
import java.util.Calendar;
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
import com.mahavastu.advisor.model.ClientLoginDetails;
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

    @Override
    public Client login(ClientLoginDetails client)
    {
        if (client == null)
            return null;
        ClientEntity clientEntity = clientRepository.findByClientEmailOrClientMobile(client.getClientId(), client.getClientId());
        if (clientEntity != null && clientEntity.getPassword().equals(client.getPassword()))
        {
            return Converter.getClientFromClientEntity(clientEntity);
        }
        return null;
    }

    @Override
    public Client addClient(Client client)
    {
        try
        {
            ClientEntity existingClientEntity = clientRepository
                    .findByClientEmailOrClientMobile(client.getClientEmail(), client.getClientMobile());
            if (existingClientEntity != null)
            {
                return null;
            }
            OccupationEntity occupationEntity = occupationRepository.getById(client.getOccupation().getOccupationId());
            System.out.println(occupationEntity);
            ClientEntity clientEntity = Converter.getClientEntityFromClient(client, occupationEntity);
            if (clientEntity != null)
            {
                clientEntity.setCreatedDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
                ClientEntity savedEntity = clientRepository.save(clientEntity);
                System.out.println("saved: " + savedEntity);
                return Converter.getClientFromClientEntity(savedEntity);
            }
            return null;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Occupation> getAllOccupations()
    {
        List<OccupationEntity> occupationEntities = occupationRepository.findAll();
        return Converter.getOccupationsFromOccupationEntities(occupationEntities);
    }

    @Override
    public Client updateClient(Client client)
    {
        ClientEntity existingClientEntity = clientRepository.getById(client.getClientId());
        if (existingClientEntity == null)
        {
            return null;
        }
        OccupationEntity occupationEntity = occupationRepository.getById(client.getOccupation().getOccupationId());
        System.out.println(occupationEntity);
        ClientEntity clientEntity = Converter.getClientEntityFromClient(client, occupationEntity);
        if (clientEntity != null)
        {
            if(StringUtils.isEmpty(client.getPassword()))
            {
                clientEntity.setPassword(existingClientEntity.getPassword());
            }
            ClientEntity savedEntity = clientRepository.save(clientEntity);
            System.out.println("saved: " + savedEntity);
            return Converter.getClientFromClientEntity(savedEntity);
        }
        return null;
    }
}
