package com.mahavastu.advisor.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Conventions;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.mahavastu.advisor.entity.ClientEntity;
import com.mahavastu.advisor.entity.converter.Converter;
import com.mahavastu.advisor.model.Client;
import com.mahavastu.advisor.model.TransportFile;
import com.mahavastu.advisor.model.UserQuery;
import com.mahavastu.advisor.repository.AdvisorAppMetadataRepositroy;
import com.mahavastu.advisor.repository.ClientRepository;

@Service
public class FileServiceImpl implements FileService
{
    @Autowired
    private UserQueryService userQueryService;
    
    // Using repository here because of password constraints.
    @Autowired
    private ClientRepository clientRepository;
    
    @Autowired
    private AdvisorAppMetadataRepositroy advisorAppMetadataRepositroy;

    private String getUploadParentPath()
    {
        return advisorAppMetadataRepositroy.findByPropertyKey("PARENT_UPLOAD_PATH").getPropertyValue();
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
            updateQuery(transportFile.getQueryId());
            return "File Uploaded";
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "Could not store the file.";
        }
    }

    private void updateQuery(Integer queryId)
    {
        UserQuery userQuery = userQueryService.getQueryById(queryId);
        userQuery.setQueryUpdateDatetime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        userQueryService.addUserQuery(userQuery);
        
    }

    @Override
    public String getFile(Integer clientId, Integer siteId, String documentType)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Client uploadProfilePic(TransportFile transportFile)
    {
        MultipartFile file = transportFile.getFile();
        Integer clientId = transportFile.getClientId();
        if(file == null || clientId == null)
        {
            return null;
        }
        try
        {
            String saveFileName = String
                    .format("profile-pics\\%s_profile-pic_%s", clientId, file.getOriginalFilename());
            
            Path root = Paths.get(getUploadParentPath());
            
            Path resolve = root.resolve(saveFileName);
            if (resolve.toFile()
                    .exists())
            {
                resolve.toFile().delete();
            }
            Files.copy(file.getInputStream(), resolve);
            
            return updateClient(transportFile.getClientId(), transportFile.getFile().getBytes());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    private Client updateClient(Integer clientId, byte[] profilePic)
    {
        ClientEntity clientEntity = clientRepository.getById(clientId);
        clientEntity.setClientDisplayPic(profilePic);
        ClientEntity savedEntity = clientRepository.save(clientEntity);
        return Converter.getClientFromClientEntity(savedEntity);
    }

}
