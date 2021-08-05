package com.mahavastu.advisor.service;

import com.mahavastu.advisor.entity.ClientEntity;
import com.mahavastu.advisor.entity.SiteEntity;
import com.mahavastu.advisor.entity.converter.Converter;
import com.mahavastu.advisor.model.Site;
import com.mahavastu.advisor.repository.ClientRepository;
import com.mahavastu.advisor.repository.SiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SiteServiceImpl implements SiteService{

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Site addSite(Site site) {
        System.out.println(site.getClient());
        ClientEntity clientEntity = clientRepository.getById(site.getClient().getClientId());
        System.out.println(clientEntity);
        SiteEntity siteEntity = Converter.buildSiteEntityFromSite(site, clientEntity);

        if(siteEntity != null) {
            System.out.println(siteEntity);
            SiteEntity savedEntity = siteRepository.save(siteEntity);
            System.out.println("savedEntity: " + savedEntity);
            return Converter.getSiteFromSiteEntity(savedEntity);
        }
        return null;
    }

    @Override
    public Site getSiteBySiteId(Integer siteId) {
        SiteEntity siteEntity = siteRepository.getById(siteId);
        return Converter.getSiteFromSiteEntity(siteEntity);
    }

    @Override
    public List<Site> getSitesByClientId(Integer clientId) {
        ClientEntity clientEntity = clientRepository.getById(clientId);
        List<SiteEntity> siteEntities = siteRepository.findByClient(clientEntity);
        return Converter.getSitesFromSiteEntities(siteEntities);
    }
}
