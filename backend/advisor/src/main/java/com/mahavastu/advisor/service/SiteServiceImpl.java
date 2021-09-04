package com.mahavastu.advisor.service;

import com.mahavastu.advisor.entity.ClientEntity;
import com.mahavastu.advisor.entity.SiteEntity;
import com.mahavastu.advisor.entity.SiteTypeEntity;
import com.mahavastu.advisor.entity.converter.Converter;
import com.mahavastu.advisor.model.Site;
import com.mahavastu.advisor.model.SiteType;
import com.mahavastu.advisor.repository.ClientRepository;
import com.mahavastu.advisor.repository.SiteRepository;
import com.mahavastu.advisor.repository.SiteTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SiteServiceImpl implements SiteService{

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private SiteTypeRepository siteTypeRepository;

    @Override
    public Site addSite(Site site) {

        ClientEntity clientEntity = clientRepository.getById(site.getClient().getClientId());
        SiteTypeEntity siteTypeEntity = siteTypeRepository.getById(site.getSiteType().getSiteTypeId());
        SiteEntity siteEntity = Converter.buildSiteEntityFromSite(site, clientEntity, siteTypeEntity);

        if(siteEntity != null) {
            System.out.println(siteEntity);
            SiteEntity savedEntity = siteRepository.save(siteEntity);
            System.out.println("savedEntity: " + savedEntity);
            return Converter.getSiteFromSiteEntity(savedEntity);
        }
        return null;
    }

    @Override
    public Site updateSite(Site site) {

        ClientEntity clientEntity = clientRepository.getById(site.getClient().getClientId());
        SiteTypeEntity siteTypeEntity = siteTypeRepository.getById(site.getSiteType().getSiteTypeId());
        SiteEntity siteEntity = Converter.getSiteEntityFromSite(site, clientEntity, siteTypeEntity);

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

    @Override
    public List<SiteType> getAllSiteTypes() {
        return Converter.getSiteTypesFromSiteTypeEntities(siteTypeRepository.findAll());
    }

    @Override
    public List<Site> getAllSites() {
        List<Site> sitesFromSiteEntities = Converter.getSitesFromSiteEntities(siteRepository.findAll());
        Collections.sort(sitesFromSiteEntities, (s1, s2) -> s1.getSiteName().compareTo(s2.getSiteName()));
        return sitesFromSiteEntities;
    }
}
