package com.mahavastu.advisor.service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mahavastu.advisor.entity.AdvisorEntity;
import com.mahavastu.advisor.entity.ClientEntity;
import com.mahavastu.advisor.entity.MasterConcernEntity;
import com.mahavastu.advisor.entity.SiteEntity;
import com.mahavastu.advisor.entity.UserQueryEntity;
import com.mahavastu.advisor.entity.converter.Converter;
import com.mahavastu.advisor.model.UserQuery;
import com.mahavastu.advisor.repository.AdvisorRepository;
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

    @Autowired
    private MailService mailService;
    
    @Autowired
    private AdvisorRepository advisorRepository;

    private static String MAIL_CONTENT_STRING = "Hi, \n\n A new query has been registered by the Client: %s (client-id: %s) for the site: %s (site-id: %s) with the text of the query as:\n\n%s: %s\n\nThanks and Regards,\nMahavastu Advisor Team";

    @Override
    public UserQuery addUserQuery(UserQuery userQuery)
    {
        ClientEntity clientEntity = clientRepository.getById(userQuery.getClient().getClientId());
        SiteEntity siteEntity = siteRepository.getById(userQuery.getSiteId());
        MasterConcernEntity masterConcernEntity = masterConcernRepository.getById(userQuery.getMasterConcern().getConcernId());

        UserQueryEntity userQueryEntity = Converter
                .getUserQueryEntityFromUserQuery(userQuery, clientEntity, siteEntity, masterConcernEntity, null);
        if (userQueryEntity == null)
        {
            return null;
        }
        Timestamp currentTimestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
        userQueryEntity.setQueryCreateDatetime(currentTimestamp);
        userQueryEntity.setQueryUpdateDatetime(currentTimestamp);
        UserQueryEntity savedEntity = userQueryRepository.save(userQueryEntity);

        new Thread(new Runnable()
        {

            @Override
            public void run()
            {
                mailService.sendQueryRegisteredMail(
                        "New Query Registered Notification",
                        String.format(
                                MAIL_CONTENT_STRING,
                                savedEntity.getClient().getClientName(),
                                savedEntity.getClient().getClientId(),
                                savedEntity.getSite().getSiteName(),
                                savedEntity.getSite().getSiteId(),
                                savedEntity.getMasterConcernEntity().getConcernName(),
                                savedEntity.getQueryText()));
            }
        }).start();

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
        List<UserQueryEntity> userQueryEntities = userQueryRepository.findBySite(siteEntity);
        List<UserQuery> userQueries = Converter.getUserQueriesFromUserQueryEntities(userQueryEntities);
        return userQueries;
    }

    @Override
    public String resolveQueryByQueryId(int userQueryId, String resolveText, int advisorId)
    {
        if(StringUtils.isEmpty(resolveText))
        {
            return "Query cannot be marked as resolved if no resolution is provided.";
        }
        try
        {
            UserQueryEntity userQueryEntity = userQueryRepository.getById(userQueryId);
            AdvisorEntity advisorEntity = advisorRepository.getById(advisorId);
            userQueryEntity.setActive(false);
            userQueryEntity.setResolveText(resolveText);
            userQueryEntity.setAdvisorEntity(advisorEntity);
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
