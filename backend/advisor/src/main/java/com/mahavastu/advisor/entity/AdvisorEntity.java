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
import lombok.ToString;

@Entity
@Table(name = "mv_advisor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdvisorEntity
{
    public AdvisorEntity(String advisorName, String advisorMobile, String advisorEmail, String password)
    {
        super();
        this.advisorName = advisorName;
        this.advisorMobile = advisorMobile;
        this.advisorEmail = advisorEmail;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer advisorId;
    private String advisorName;
    private String advisorMobile;
    private String advisorEmail;
    private String password;

}
