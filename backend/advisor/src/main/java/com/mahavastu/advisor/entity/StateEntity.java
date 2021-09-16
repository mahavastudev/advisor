package com.mahavastu.advisor.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "mv_master_state")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StateEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer stateId;
    private String code;
    private String name;
    private String counCode;
}
