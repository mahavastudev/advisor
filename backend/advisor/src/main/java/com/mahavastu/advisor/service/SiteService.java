package com.mahavastu.advisor.service;

import com.mahavastu.advisor.model.SearchElement;
import com.mahavastu.advisor.model.Site;
import com.mahavastu.advisor.model.SiteType;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

public interface SiteService {
    Site addSite(Site site);
    Site getSiteBySiteId(Integer siteId);
    List<Site> getSitesByClientId(Integer clientId);
    List<SiteType> getAllSiteTypes();
    Site updateSite(Site site);
    List<Site> getAllSites();
    List<Site> getFilteredSites(SearchElement searchElement);
    void getSiteOwnerPatri(HttpServletResponse response, Integer siteId);
}
