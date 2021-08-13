package com.mahavastu.advisor.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
