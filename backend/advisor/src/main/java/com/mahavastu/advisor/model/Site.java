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
    private String siteAddress;
    private String siteGeo;
    private Integer siteTypeId;
    private Integer siteMapId;
    private Client client;
    private String conditionType;
}
