package com.mahavastu.advisor.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "mv_advisor_app_metadata")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdvisorAppMetaDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String propertyKey;

    private String propertyValue;
    
}
