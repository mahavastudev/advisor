package com.mahavastu.advisor.service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mahavastu.advisor.entity.ClientEntity;
import com.mahavastu.advisor.entity.MasterConcernEntity;
import com.mahavastu.advisor.entity.SiteEntity;
import com.mahavastu.advisor.entity.UserQueryEntity;
import com.mahavastu.advisor.entity.converter.Converter;
import com.mahavastu.advisor.model.UserQuery;
import com.mahavastu.advisor.repository.ClientRepository;
import com.mahavastu.advisor.repository.MasterConcernRepository;
import com.mahavastu.advisor.repository.SiteRepository;
import com.mahavastu.advisor.repository.UserQueryRepository;

@Service
public class UserQueryServiceImpl implements UserQueryService
{

    @Autowired
    private UserQueryRepository userQueryRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private MasterConcernRepository masterConcernRepository;

    @Override
    public UserQuery addUserQuery(UserQuery userQuery)
    {
        ClientEntity clientEntity = clientRepository.getById(userQuery.getClient().getClientId());
        SiteEntity siteEntity = siteRepository.getById(userQuery.getSiteId());
        MasterConcernEntity masterConcernEntity = masterConcernRepository.getById(userQuery.getMasterConcern().getConcernId());

        UserQueryEntity userQueryEntity = Converter
                .getUserQueryEntityFromUserQuery(userQuery, clientEntity, siteEntity, masterConcernEntity);
        if (userQueryEntity == null)
        {
            return null;
        }
        Timestamp currentTimestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
        userQueryEntity.setQueryCreateDatetime(currentTimestamp);
        userQueryEntity.setQueryUpdateDatetime(currentTimestamp);
        UserQueryEntity savedEntity = userQueryRepository.save(userQueryEntity);
        return Converter.getUserQueryFromUserQueryEntity(savedEntity);

    }

    @Override
    public List<UserQuery> getUserQueriesByClientId(Integer clientId)
    {
        ClientEntity clientEntity = clientRepository.getById(clientId);
        List<UserQueryEntity> userQueryEntities = userQueryRepository.findByClientAndIsActive(clientEntity, true);
        List<UserQuery> userQueries = Converter.getUserQueriesFromUserQueryEntities(userQueryEntities);
        return userQueries;
    }

    @Override
    public UserQuery getQueryById(Integer userQueryId)
    {
        return Converter.getUserQueryFromUserQueryEntity(userQueryRepository.getById(userQueryId));
    }

    @Override
    public List<UserQuery> getAllQueries()
    {
        return Converter.getUserQueriesFromUserQueryEntities(userQueryRepository.findByIsActive(true));
    }

    @Override
    public List<UserQuery> getUserQueriesBySiteId(int siteId)
    {
        SiteEntity siteEntity = siteRepository.getById(siteId);
        List<UserQueryEntity> userQueryEntities = userQueryRepository.findBySiteAndIsActive(siteEntity, true);
        List<UserQuery> userQueries = Converter.getUserQueriesFromUserQueryEntities(userQueryEntities);
        return userQueries;
    }

    @Override
    public String resolveQueryByQueryId(int userQueryId)
    {
        try
        {
            UserQueryEntity userQueryEntity = userQueryRepository.getById(userQueryId);
            userQueryEntity.setActive(false);
            UserQueryEntity savedEntity = userQueryRepository.save(userQueryEntity);
            return String.format(
                    "Query related to site %s for concern %s, and Query id as %s is marked as resolved.",
                    savedEntity.getSite().getSiteName(),
                    savedEntity.getMasterConcernEntity().getConcernName(),
                    userQueryId);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return String.format(
                    "Query with id as %s could not be marked as resolved.",
                    userQueryId);
        }
    }
}
