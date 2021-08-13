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
public class Client {

    private Integer clientId;
    private String clientName;
    private String clientMobile;
    private String clientEmail;
    private String clientDisplayPic;
    private String clientPOC;
    private Occupation occupation;
    private String password;
}
