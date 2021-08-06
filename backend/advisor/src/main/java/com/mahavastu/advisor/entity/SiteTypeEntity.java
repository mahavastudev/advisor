package com.mahavastu.advisor.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mv_site_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SiteTypeEntity {

    @Id
    private Integer siteTypeId;

    private String siteTypeName;
}
