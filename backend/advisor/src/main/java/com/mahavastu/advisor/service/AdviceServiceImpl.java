package com.mahavastu.advisor.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.mahavastu.advisor.entity.ClientEntity;
import com.mahavastu.advisor.entity.SiteEntity;
import com.mahavastu.advisor.entity.UserQueryEntity;
import com.mahavastu.advisor.entity.advice.AdviceEntity;
import com.mahavastu.advisor.entity.converter.Converter;
import com.mahavastu.advisor.model.Advice;
import com.mahavastu.advisor.model.LevelEnum;
import com.mahavastu.advisor.model.RequestResult;
import com.mahavastu.advisor.repository.AdviceRepository;
import com.mahavastu.advisor.repository.ClientRepository;
import com.mahavastu.advisor.repository.SiteRepository;
import com.mahavastu.advisor.repository.UserQueryRepository;
import com.mahavastu.advisor.utility.EnumUtility;

@Service
public class AdviceServiceImpl implements AdviceService{

    @Autowired
    private AdviceRepository adviceRepository;

    @Autowired
    private UserQueryRepository userQueryRepository;

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private ClientRepository clientRepository;


    @Override
    public RequestResult advice(List<Advice> advices) {
        String message = "";
        if(CollectionUtils.isEmpty(advices) || advices.iterator().next() == null) {
            message = "Advice could not be updated! Please try again!";
        }
        else {
            Advice firstAdvice = advices.iterator().next();
            UserQueryEntity userQueryEntity = userQueryRepository.getById(firstAdvice.getUserQuery().getQueryId());
            SiteEntity siteEntity = siteRepository.getById(firstAdvice.getUserQuery().getSiteId());
            ClientEntity clientEntity = clientRepository.getById(firstAdvice.getUserQuery().getClient().getClientId());

            List<AdviceEntity> adviceEntities = Converter.getAdviceEntitiesFromAdvices(advices, userQueryEntity, siteEntity, clientEntity);

            if(!CollectionUtils.isEmpty(adviceEntities)) {
                List<AdviceEntity> savedEntities = adviceRepository.saveAll(adviceEntities);
                AdviceEntity firstSavedEntity = savedEntities.iterator().next();
                message = String.format("Advice updated for Query-Id: %s , Client : %s , Site : %s",
                        firstSavedEntity.getSiteQueryCompositeKey().getUserQueryEntity().getQueryId(),
                        firstSavedEntity.getSiteQueryCompositeKey().getUserQueryEntity().getClient().getClientName(),
                        firstSavedEntity.getSiteQueryCompositeKey().getSiteEntity().getSiteName() + " - " + firstSavedEntity.getSiteQueryCompositeKey().getSiteEntity().getSiteAddress());
            }
            else {
                message = "Advice could not be updated! Please try again!";
            }
        }
        return new RequestResult(message);
    }

    @Override
    public List<Advice> getAdvices(Integer queryId, Integer siteId, LevelEnum levelEnum) {
        List<AdviceEntity> adviceEntities = adviceRepository.getAdvicesForQuerySiteAndLevel(queryId, siteId, levelEnum);
        List<Advice> advices = Converter.getAdvicesFromAdviceEntities(adviceEntities);
        return advices;
    }
}
