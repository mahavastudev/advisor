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
public class Country
{
    private Integer countryId;
    private String code;
    private String name;
    private Integer isStateList;
}
