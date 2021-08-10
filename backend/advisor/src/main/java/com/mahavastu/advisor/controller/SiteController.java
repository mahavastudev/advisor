package com.mahavastu.advisor.controller;

import com.mahavastu.advisor.model.Site;
import com.mahavastu.advisor.model.SiteType;
import com.mahavastu.advisor.service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("site")
public class SiteController {

    @Autowired
    private SiteService siteService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Site addSite(@RequestBody Site site) {
        return siteService.addSite(site);
    }

    @GetMapping
    public List<Site> getAllSites() {
        return siteService.getAllSites();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Site updateSite(@RequestBody Site site) {
        return siteService.updateSite(site);
    }

    @GetMapping("/{siteId}")
    @ResponseBody
    public Site getSiteBySiteId(@PathVariable("siteId") Integer siteId) {
        return siteService.getSiteBySiteId(siteId);
    }

    @GetMapping("/client/{clientId}")
    @ResponseBody
    public List<Site> getSitesByClientId(@PathVariable("clientId") Integer clientId) {
        return siteService.getSitesByClientId(clientId);
    }

    @GetMapping("/site-types")
    @ResponseBody
    public List<SiteType> getSiteTypes() {
        return siteService.getAllSiteTypes();
    }

}
