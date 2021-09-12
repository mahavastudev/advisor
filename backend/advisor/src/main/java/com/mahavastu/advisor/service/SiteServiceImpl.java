package com.mahavastu.advisor.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mahavastu.advisor.entity.AddressEntity;
import com.mahavastu.advisor.entity.AdvisorEntity;
import com.mahavastu.advisor.entity.ClientEntity;
import com.mahavastu.advisor.entity.SiteEntity;
import com.mahavastu.advisor.entity.SiteTypeEntity;
import com.mahavastu.advisor.entity.converter.Converter;
import com.mahavastu.advisor.model.Address;
import com.mahavastu.advisor.model.SearchElement;
import com.mahavastu.advisor.model.Site;
import com.mahavastu.advisor.model.SiteType;
import com.mahavastu.advisor.repository.AddressRepository;
import com.mahavastu.advisor.repository.AdvisorRepository;
import com.mahavastu.advisor.repository.ClientRepository;
import com.mahavastu.advisor.repository.SiteRepository;
import com.mahavastu.advisor.repository.SiteTypeRepository;

@Service
public class SiteServiceImpl implements SiteService
{

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private SiteTypeRepository siteTypeRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AdvisorRepository advisorRepository;

    @Override
    public Site addSite(Site site)
    {

        ClientEntity clientEntity = null;
        if (site.getClient().getClientId() == null)
        {
            clientEntity = clientRepository
                    .findByClientEmailOrClientMobile(site.getClient().getClientEmail(), site.getClient().getClientEmail());
        }
        else
        {
            clientEntity = clientRepository.getById(site.getClient().getClientId());
            ;
        }
        SiteTypeEntity siteTypeEntity = siteTypeRepository.getById(site.getSiteType().getSiteTypeId());

        AddressEntity savedAddressEntity = addAddress(site.getAddress());

        AdvisorEntity advisorEntity = (site.getCreatedByAdvisor() == null || site.getCreatedByAdvisor().getAdvisorId() == null)
                ? null
                : advisorRepository.getById(site.getCreatedByAdvisor().getAdvisorId());

        SiteEntity siteEntity = Converter
                .buildSiteEntityFromSite(site, clientEntity, siteTypeEntity, savedAddressEntity, advisorEntity);

        if (siteEntity != null)
        {
            System.out.println(siteEntity);
            SiteEntity savedEntity = siteRepository.save(siteEntity);
            System.out.println("savedEntity: " + savedEntity);
            return Converter.getSiteFromSiteEntity(savedEntity);
        }
        return null;
    }

    private AddressEntity addAddress(Address address)
    {
        if (address == null)
        {
            return null;
        }
        AddressEntity addressEntity = Converter.getAddressEntityFromAddress(address);
        return addressRepository.save(addressEntity);
    }

    @Override
    public Site updateSite(Site site)
    {

        ClientEntity clientEntity = clientRepository.getById(site.getClient().getClientId());
        SiteTypeEntity siteTypeEntity = siteTypeRepository.getById(site.getSiteType().getSiteTypeId());
        AddressEntity addressEntity = addressRepository.getById(site.getAddress().getAddressId());
        SiteEntity existingSiteEnitity = siteRepository.getById(site.getSiteId());
        AdvisorEntity createdByAdvisorEntity = existingSiteEnitity.getCreatedByAdvisorEntity();

        SiteEntity siteEntity = Converter
                .getSiteEntityFromSite(site, clientEntity, siteTypeEntity, addressEntity, createdByAdvisorEntity);

        if (siteEntity != null)
        {
            System.out.println(siteEntity);
            SiteEntity savedEntity = siteRepository.save(siteEntity);
            System.out.println("savedEntity: " + savedEntity);
            return Converter.getSiteFromSiteEntity(savedEntity);
        }
        return null;
    }

    @Override
    public Site getSiteBySiteId(Integer siteId)
    {
        SiteEntity siteEntity = siteRepository.getById(siteId);
        return Converter.getSiteFromSiteEntity(siteEntity);
    }

    @Override
    public List<Site> getSitesByClientId(Integer clientId)
    {
        ClientEntity clientEntity = clientRepository.getById(clientId);
        List<SiteEntity> siteEntities = siteRepository.findByClient(clientEntity);
        return Converter.getSitesFromSiteEntities(siteEntities);
    }

    @Override
    public List<SiteType> getAllSiteTypes()
    {
        return Converter.getSiteTypesFromSiteTypeEntities(siteTypeRepository.findAll());
    }

    @Override
    public List<Site> getAllSites()
    {
        List<Site> sitesFromSiteEntities = Converter.getSitesFromSiteEntities(siteRepository.findAll());
        Collections.sort(sitesFromSiteEntities, (s1, s2) -> s1.getSiteName().compareTo(s2.getSiteName()));
        return sitesFromSiteEntities;
    }

    @Override
    public List<Site> getFilteredSites(SearchElement searchElement)
    {

        List<SiteEntity> siteEntities = siteRepository.findAll();
        if ((searchElement == null)
                || (StringUtils.isEmpty(searchElement.getCity())
                        && StringUtils.isEmpty(searchElement.getCountry())
                        && StringUtils.isEmpty(searchElement.getMaxSize())
                        && StringUtils.isEmpty(searchElement.getMinSize())
                        && StringUtils.isEmpty(searchElement.getState())
                        && StringUtils.isEmpty(searchElement.getSubCity())
                        && StringUtils.isEmpty(searchElement.getType())))
        {
            return Converter.getSitesFromSiteEntities(siteEntities);
        }

        Stream<SiteEntity> siteEntitiesStream = siteEntities.stream();
        if (!StringUtils.isEmpty(searchElement.getCountry()))
        {
            siteEntitiesStream = siteEntitiesStream
                    .filter(e -> e.getAddressEntity().getCountry().equalsIgnoreCase(searchElement.getCountry()));
        }
        if (!StringUtils.isEmpty(searchElement.getMaxSize()))
        {
            siteEntitiesStream = siteEntitiesStream
                    .filter(e -> Integer.parseInt(e.getCoveredArea()) <= Integer.parseInt(searchElement.getMaxSize()));
        }
        if (!StringUtils.isEmpty(searchElement.getMaxSize()))
        {
            siteEntitiesStream = siteEntitiesStream
                    .filter(e -> Integer.parseInt(e.getCoveredArea()) >= Integer.parseInt(searchElement.getMinSize()));
        }
        if (!StringUtils.isEmpty(searchElement.getState()))
        {
            siteEntitiesStream = siteEntitiesStream
                    .filter(e -> e.getAddressEntity().getState().equalsIgnoreCase(searchElement.getState()));
        }
        if (!StringUtils.isEmpty(searchElement.getSubCity()))
        {
            siteEntitiesStream = siteEntitiesStream
                    .filter(e -> e.getAddressEntity().getSubCity().equalsIgnoreCase(searchElement.getSubCity()));
        }
        if (!StringUtils.isEmpty(searchElement.getType()))
        {
            siteEntitiesStream = siteEntitiesStream
                    .filter(e -> e.getSiteType().getSiteTypeName().equalsIgnoreCase(searchElement.getType()));
        }
        if (!StringUtils.isEmpty(searchElement.getCity()))
        {
            siteEntitiesStream = siteEntitiesStream
                    .filter(e -> e.getAddressEntity().getCity().equalsIgnoreCase(searchElement.getCity()));
        }
        return Converter.getSitesFromSiteEntities(siteEntitiesStream.collect(Collectors.toList()));
    }

}
