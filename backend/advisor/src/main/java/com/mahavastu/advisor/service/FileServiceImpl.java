package com.mahavastu.advisor.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.mahavastu.advisor.model.TransportFile;

@Service
public class FileServiceImpl implements FileService
{
    private String getUploadParentPath()
    {
        return "C:\\Mahavastu\\Advisor\\upload\\";
    }

    @Override
    public String uploadFile(TransportFile transportFile)
    {
        MultipartFile file = transportFile.getFile();
        Integer clientId = transportFile.getClientId();
        Integer siteId = transportFile.getSiteId();
        String documentType = transportFile.getDocumentType();
        if(file == null || clientId == null || siteId == null || !StringUtils.hasText(documentType))
        {
            return "Could not store the file as incorrect parameters provided.";
        }
        try
        {
            String saveFileName = String
                    .format("%s_%s_%s_%s", clientId, siteId, documentType, file.getOriginalFilename());
            
            Path root = Paths.get(getUploadParentPath());
            
            Path resolve = root.resolve(saveFileName);
            if (resolve.toFile()
                    .exists())
            {
                return String.format("File: %s already exists. Ask Admin to remove the same.", file.getOriginalFilename());
            }
            Files.copy(file.getInputStream(), resolve);
            return "File Uploaded";
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "Could not store the file.";
        }
    }

    @Override
    public String getFile(Integer clientId, Integer siteId, String documentType)
    {
        // TODO Auto-generated method stub
        return null;
    }

}
