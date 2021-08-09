package com.mahavastu.advisor.service;

import com.mahavastu.advisor.entity.ClientEntity;
import com.mahavastu.advisor.entity.SiteEntity;
import com.mahavastu.advisor.entity.UserQueryEntity;
import com.mahavastu.advisor.entity.advice.AdviceEntity;
import com.mahavastu.advisor.entity.advice.SiteQueryCompositeKey;
import com.mahavastu.advisor.entity.converter.Converter;
import com.mahavastu.advisor.model.Advice;
import com.mahavastu.advisor.model.LevelEnum;
import com.mahavastu.advisor.repository.AdviceRepository;
import com.mahavastu.advisor.repository.ClientRepository;
import com.mahavastu.advisor.repository.SiteRepository;
import com.mahavastu.advisor.repository.UserQueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

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
    public String advice(List<Advice> advices) {
        if(CollectionUtils.isEmpty(advices) || advices.iterator().next() == null) {
            return "Advice could not be updated! Please try again!";
        }
        Advice firstAdvice = advices.iterator().next();
        UserQueryEntity userQueryEntity = userQueryRepository.getById(firstAdvice.getUserQuery().getQueryId());
        SiteEntity siteEntity = siteRepository.getById(firstAdvice.getSite().getSiteId());
        ClientEntity clientEntity = clientRepository.getById(firstAdvice.getUserQuery().getClient().getClientId());

        List<AdviceEntity> adviceEntities = Converter.getAdviceEntitiesFromAdvices(advices, userQueryEntity, siteEntity, clientEntity);

        if(!CollectionUtils.isEmpty(adviceEntities)) {
            List<AdviceEntity> savedEntities = adviceRepository.saveAll(adviceEntities);
            AdviceEntity firstSavedEntity = savedEntities.iterator().next();
            return String.format("Advice updated for Query-Id: %s , Client : %s , Site : %s",
                    firstSavedEntity.getSiteQueryCompositeKey().getUserQueryEntity().getQueryId(),
                    firstSavedEntity.getSiteQueryCompositeKey().getUserQueryEntity().getClient().getClientName(),
                    firstSavedEntity.getSiteQueryCompositeKey().getSiteEntity().getSiteName() + " - " + firstSavedEntity.getSiteQueryCompositeKey().getSiteEntity().getSiteAddress());
        }
        else {
            return "Advice could not be updated! Please try again!";
        }
    }

    @Override
    public List<Advice> getAdvices(Integer queryId, Integer siteId, LevelEnum level) {

        List<AdviceEntity> adviceEntities = adviceRepository.getAdvicesForQuerySiteAndLevel(queryId, siteId, level.toString());
        List<Advice> advices = Converter.getAdvicesFromAdviceEntities(adviceEntities);
        return advices;
    }
}
