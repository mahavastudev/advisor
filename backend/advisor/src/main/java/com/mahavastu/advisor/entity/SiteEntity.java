package com.mahavastu.advisor.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "mv_site")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SiteEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer siteId;
    private String siteName;
    
    @OneToOne
    @JoinColumn(name = "site_type_id")
    private SiteTypeEntity siteType;
    private Integer siteMapId;

    @OneToOne
    @JoinColumn(name = "cl_id")
    private ClientEntity client;

    private String conditionType;

    private String plotArea;
    private String coveredArea;
    
    @OneToOne
    @JoinColumn(name = "address_id")
    private AddressEntity addressEntity;

    private String fileNumber;
    private String locationOfFile;
    
    @OneToOne
    @JoinColumn(name = "created_by_advisor_id")
    private AdvisorEntity createdByAdvisorEntity;
    
    public SiteEntity(
            String siteName,
            SiteTypeEntity siteType,
            Integer siteMapId,
            ClientEntity client,
            String conditionType,
            String plotArea,
            String coveredArea,
            AddressEntity addressEntity,
            String fileNumber,
            String locationOfFile)
    {
        this.siteName = siteName;
        this.siteType = siteType;
        this.siteMapId = siteMapId;
        this.client = client;
        this.conditionType = conditionType;
        this.plotArea = plotArea;
        this.coveredArea = coveredArea;
        this.addressEntity = addressEntity;
        this.fileNumber = fileNumber;
        this.locationOfFile = locationOfFile;
    }
}
