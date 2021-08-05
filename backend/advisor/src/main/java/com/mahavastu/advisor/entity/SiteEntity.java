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
    private Integer siteTypeId;
    private Integer siteMapId;
    @OneToOne
    @JoinColumn(name = "cl_id")
    private ClientEntity client;

    private String conditionType;

    public SiteEntity(String siteName, String siteAddress, String siteGeo, Integer siteTypeId, Integer siteMapId, ClientEntity client, String conditionType) {
        this.siteName = siteName;
        this.siteAddress = siteAddress;
        this.siteGeo = siteGeo;
        this.siteTypeId = siteTypeId;
        this.siteMapId = siteMapId;
        this.client = client;
        this.conditionType = conditionType;
    }
}
