package com.mahavastu.advisor.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mv_master_concern")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MasterConcernEntity {
    @Id
    private Integer concernId;

    private String concernName;
}
