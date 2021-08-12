package com.mahavastu.advisor.model;

import org.springframework.web.multipart.MultipartFile;

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
public class TransportFile
{
    private MultipartFile file;
    private Integer clientId;
    private Integer siteId;
    private String documentType;
}
