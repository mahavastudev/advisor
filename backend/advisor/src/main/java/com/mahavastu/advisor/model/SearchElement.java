package com.mahavastu.advisor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SearchElement
{

    // Site
    private String minCoveredAreaSize;
    private String maxCoveredAreaSize;
    private String minPlotAreaSize;
    private String maxPlotAreaSize;
    private String city;
    private String country;
    private String state;
    private String subCity;
    private String siteType;
    
    // Client
    private String clientEmail;
    private String clientName;
    private String clientPhone;
    
    // User-Query
    private String queryConcern;
}
