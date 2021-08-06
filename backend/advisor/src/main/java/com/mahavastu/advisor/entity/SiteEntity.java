package com.mahavastu.advisor.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "mv_site")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SiteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer siteId;
    private String siteName;
    private String siteAddress;
    private String siteGeo;
    @OneToOne
    @JoinColumn(name = "site_type_id")
    private SiteTypeEntity siteType;
    private Integer siteMapId;
    @OneToOne
    @JoinColumn(name = "cl_id")
    private ClientEntity client;

    private String conditionType;

    public SiteEntity(String siteName, String siteAddress, String siteGeo, SiteTypeEntity siteType, Integer siteMapId, ClientEntity client, String conditionType) {
        this.siteName = siteName;
        this.siteAddress = siteAddress;
        this.siteGeo = siteGeo;
        this.siteType = siteType;
        this.siteMapId = siteMapId;
        this.client = client;
        this.conditionType = conditionType;
    }
}
