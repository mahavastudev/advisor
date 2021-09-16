package com.mahavastu.advisor.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

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
import com.mahavastu.advisor.utility.FileUtility;

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

    private final static String PATRI_URL = "https://vedicastrology1.mahavastu.com/vedic.astrology/vedicAstro/vedicAstroByTemplate?productId=25&languageId=2&username=advisorPortal&password=somePassword"
            + "&name=%s&dob=%s&city=%s&state=%s&country=%s&latitude=&longitude=";

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
                || (StringUtils.isEmpty(searchElement.getClientEmail())
                        && StringUtils.isEmpty(searchElement.getClientName())
                        && StringUtils.isEmpty(searchElement.getClientPhone())
                        && StringUtils.isEmpty(searchElement.getCity())
                        && StringUtils.isEmpty(searchElement.getCountry())
                        && StringUtils.isEmpty(searchElement.getMinCoveredAreaSize())
                        && StringUtils.isEmpty(searchElement.getMaxCoveredAreaSize())
                        && StringUtils.isEmpty(searchElement.getMinPlotAreaSize())
                        && StringUtils.isEmpty(searchElement.getMaxPlotAreaSize())
                        && StringUtils.isEmpty(searchElement.getState())
                        && StringUtils.isEmpty(searchElement.getSubCity())
                        && StringUtils.isEmpty(searchElement.getSiteType())))
        {
            return Converter.getSitesFromSiteEntities(siteEntities);
        }

        Stream<SiteEntity> siteEntitiesStream = siteEntities.stream();

        // Client
        if (!StringUtils.isEmpty(searchElement.getClientEmail()))
        {
            siteEntitiesStream = siteEntitiesStream
                    .filter(e -> e.getClient().getClientEmail().equalsIgnoreCase(searchElement.getClientEmail()));
        }
        if (!StringUtils.isEmpty(searchElement.getClientName()))
        {
            siteEntitiesStream = siteEntitiesStream
                    .filter(e -> e.getClient().getClientName().equalsIgnoreCase(searchElement.getClientName()));
        }
        if (!StringUtils.isEmpty(searchElement.getClientPhone()))
        {
            siteEntitiesStream = siteEntitiesStream
                    .filter(e -> e.getClient().getClientMobile().equalsIgnoreCase(searchElement.getClientPhone()));
        }

        // Site
        if (!StringUtils.isEmpty(searchElement.getCountry()))
        {
            siteEntitiesStream = siteEntitiesStream
                    .filter(e -> e.getAddressEntity().getCountry().equalsIgnoreCase(searchElement.getCountry()));
        }
        if (!StringUtils.isEmpty(searchElement.getMaxCoveredAreaSize()))
        {
            siteEntitiesStream = siteEntitiesStream
                    .filter(e -> Integer.parseInt(e.getCoveredArea()) <= Integer.parseInt(searchElement.getMaxCoveredAreaSize()));
        }
        if (!StringUtils.isEmpty(searchElement.getMinCoveredAreaSize()))
        {
            siteEntitiesStream = siteEntitiesStream
                    .filter(e -> Integer.parseInt(e.getCoveredArea()) >= Integer.parseInt(searchElement.getMinCoveredAreaSize()));
        }
        if (!StringUtils.isEmpty(searchElement.getMinPlotAreaSize()))
        {
            siteEntitiesStream = siteEntitiesStream
                    .filter(e -> Integer.parseInt(e.getPlotArea()) <= Integer.parseInt(searchElement.getMinPlotAreaSize()));
        }
        if (!StringUtils.isEmpty(searchElement.getMaxPlotAreaSize()))
        {
            siteEntitiesStream = siteEntitiesStream
                    .filter(e -> Integer.parseInt(e.getPlotArea()) >= Integer.parseInt(searchElement.getMaxPlotAreaSize()));
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
        if (!StringUtils.isEmpty(searchElement.getSiteType()))
        {
            siteEntitiesStream = siteEntitiesStream
                    .filter(e -> e.getSiteType().getSiteTypeName().equalsIgnoreCase(searchElement.getSiteType()));
        }
        if (!StringUtils.isEmpty(searchElement.getCity()))
        {
            siteEntitiesStream = siteEntitiesStream
                    .filter(e -> e.getAddressEntity().getCity().equalsIgnoreCase(searchElement.getCity()));
        }
        return Converter.getSitesFromSiteEntities(siteEntitiesStream.collect(Collectors.toList()));
    }

    @Override
    public void getSiteOwnerPatri(HttpServletResponse response, Integer siteId)
    {
        SiteEntity siteEntity = siteRepository.getById(siteId);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add(
                "user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        // &name=%s&dob=%s&city=%s&state=%s&country=%s&latitude=&longitude=

        String url = String.format(
                PATRI_URL,
                siteEntity.getClient().getClientName(),
                siteEntity.getClient().getTimeStampOfBirth(),
                siteEntity.getClient().getPlaceOfBirth().getCity(),
                siteEntity.getClient().getPlaceOfBirth().getState(),
                siteEntity.getClient().getPlaceOfBirth().getCountry());

        ResponseEntity<byte[]> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                byte[].class);
        if (responseEntity.getStatusCode() == HttpStatus.OK)
        {
            byte[] patri = responseEntity.getBody();
            FileUtility.updateResponseWithFileBytes(response, patri);
        }
    }

}
