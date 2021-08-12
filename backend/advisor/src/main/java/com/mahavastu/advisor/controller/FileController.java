package com.mahavastu.advisor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahavastu.advisor.model.TransportFile;
import com.mahavastu.advisor.service.FileService;

@RestController
@RequestMapping("files")
public class FileController
{
    @Autowired
    private FileService fileService;
    
    @PostMapping
    public String uploadFile(@RequestBody TransportFile transportFile)
    {
        return fileService.uploadFile(transportFile);
    }
}
