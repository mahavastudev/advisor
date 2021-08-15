package com.mahavastu.advisor.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mahavastu.advisor.model.Client;
import com.mahavastu.advisor.model.LevelEnum;
import com.mahavastu.advisor.model.TransportFile;
import com.mahavastu.advisor.service.FileService;

@RestController
@CrossOrigin(origins = {
        "http://localhost:4200", "http://horo3.mahavastu.com:8080"
})
@RequestMapping("files")
public class FileController
{
    @Autowired
    private FileService fileService;

    @PostMapping("/query-pdfs")
    public String uploadFile(@ModelAttribute TransportFile transportFile)
    {
        return fileService.uploadFile(transportFile);
    }

    @PostMapping("/profile-pic")
    public Client uploadProfilePic(@ModelAttribute TransportFile transportFile)
    {
        return fileService.uploadProfilePic(transportFile);
    }

    @GetMapping("/query-pdfs/{query-id}/{site-id}/{client-id}/{document-type}")
    public void getQueryPdf(
            HttpServletRequest request,
            HttpServletResponse response,
            @PathVariable("query-id") Integer queryId,
            @PathVariable("site-id") Integer siteId,
            @PathVariable("client-id") Integer clientId,
            @PathVariable("document-type") String documentType)
    {
        fileService.getFile(request, response, clientId, siteId, queryId, documentType);
    }
}
