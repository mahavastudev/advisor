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
