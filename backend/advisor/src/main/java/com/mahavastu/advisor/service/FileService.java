package com.mahavastu.advisor.service;

import com.mahavastu.advisor.model.TransportFile;

public interface FileService
{
    String uploadFile(TransportFile transportFile);
    String getFile(Integer clientId, Integer siteId, String documentType);
}
