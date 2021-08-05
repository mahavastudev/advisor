package com.mahavastu.advisor.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mv_master_occupation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OccupationEntity {
    @Id
    @Column(name = "occupation_id")
    private Integer occupationId;

    @Column(name = "occupation_name")
    private String occupationName;
}
