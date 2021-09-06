package com.mahavastu.advisor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Address
{
    private Integer addressId;
    private String siteAddress;
    
    private String siteGeo;
    private String subCity;
    
    private String city;
    private String state;
    
    private String country;
    private String pinCode;
}
