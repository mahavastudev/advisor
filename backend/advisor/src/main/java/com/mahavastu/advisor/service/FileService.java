package com.mahavastu.advisor.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.mahavastu.advisor.model.Client;
import com.mahavastu.advisor.model.TransportFile;

public interface FileService
{
    String uploadFile(TransportFile transportFile);
    void getFile(
            HttpServletRequest request,
            HttpServletResponse response,
            Integer clientId,
            Integer siteId,
            Integer queryId,
            String documentType);
    Client uploadProfilePic(TransportFile transportFile);
}
