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
        if(!file.getOriginalFilename().endsWith(".pdf")
                && !file.getOriginalFilename().endsWith(".PDF"))
        {
            return "Please upload PDF's only.";
        }
        try
        {
            String saveFileName = String
                    .format("%s_%s_%s.pdf", clientId, siteId, documentType);
            
            Path root = Paths.get(getUploadParentPath());
            
            Path resolve = root.resolve(saveFileName);
            if (resolve.toFile()
                    .exists())
            {
                resolve.toFile().delete();
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
