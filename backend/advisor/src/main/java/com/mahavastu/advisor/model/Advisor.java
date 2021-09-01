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
public class Advisor
{
    private Integer advisorId;
    private String advisorName;
    private String advisorMobile;
    private String advisorEmail;
    private String password;
}
