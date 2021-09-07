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
@Table(name = "mv_address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddressEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer addressId;
    private String address;
    
    private String siteGeo;
    private String subCity;
    
    private String city;
    private String state;
    
    private String country;
    private String pinCode;
    
    public AddressEntity(
            String siteAddress,
            String siteGeo,
            String subCity,
            String city,
            String state,
            String country,
            String pinCode)
    {
        super();
        this.address = siteAddress;
        this.siteGeo = siteGeo;
        this.subCity = subCity;
        this.city = city;
        this.state = state;
        this.country = country;
        this.pinCode = pinCode;
    }
    
    
}
