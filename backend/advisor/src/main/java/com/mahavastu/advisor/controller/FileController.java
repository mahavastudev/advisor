package com.mahavastu.advisor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahavastu.advisor.model.TransportFile;
import com.mahavastu.advisor.service.FileService;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://horo3.mahavastu.com:8080"})
@RequestMapping("files")
public class FileController
{
    @Autowired
    private FileService fileService;
    
    @PostMapping
    public String uploadFile(@ModelAttribute TransportFile transportFile)
    {
        return fileService.uploadFile(transportFile);
    }
}
