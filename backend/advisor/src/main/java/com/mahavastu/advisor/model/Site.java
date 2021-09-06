package com.mahavastu.advisor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Site {
    private Integer siteId;
    private String siteName;
    private SiteType siteType;
    private Integer siteMapId;
    private Client client;
    private String conditionType;
    private String plotArea;
    private String coveredArea;
    private Address address;
}
