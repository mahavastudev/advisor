package com.mahavastu.advisor.service;

import com.mahavastu.advisor.model.Site;

import java.util.List;

public interface SiteService {
    Site addSite(Site site);
    Site getSiteBySiteId(Integer siteId);
    List<Site> getSitesByClientId(Integer clientId);
}
