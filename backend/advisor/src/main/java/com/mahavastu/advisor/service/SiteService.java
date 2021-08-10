package com.mahavastu.advisor.service;

import com.mahavastu.advisor.model.Site;
import com.mahavastu.advisor.model.SiteType;

import java.util.List;

public interface SiteService {
    Site addSite(Site site);
    Site getSiteBySiteId(Integer siteId);
    List<Site> getSitesByClientId(Integer clientId);
    List<SiteType> getAllSiteTypes();
    Site updateSite(Site site);
    List<Site> getAllSites();
}
