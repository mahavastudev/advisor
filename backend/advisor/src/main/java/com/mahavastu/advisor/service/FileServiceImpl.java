package com.mahavastu.advisor.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.mahavastu.advisor.entity.ClientEntity;
import com.mahavastu.advisor.entity.ClientImageMasterEntity;
import com.mahavastu.advisor.entity.converter.Converter;
import com.mahavastu.advisor.model.Client;
import com.mahavastu.advisor.model.Site;
import com.mahavastu.advisor.model.TransportFile;
import com.mahavastu.advisor.model.UserQuery;
import com.mahavastu.advisor.repository.AdvisorAppMetadataRepositroy;
import com.mahavastu.advisor.repository.ClientImageMasterRepository;
import com.mahavastu.advisor.repository.ClientRepository;
import com.mahavastu.advisor.utility.FileUtility;

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

    @Autowired
    private ClientImageMasterRepository clientImageMasterRepository;

    @Autowired
    private SiteService siteService;

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
        Integer queryId = transportFile.getQueryId();
        if (file == null || clientId == null || siteId == null || !StringUtils.hasText(documentType))
        {
            return "Could not store the file as incorrect parameters provided.";
        }
        if (!file.getOriginalFilename().endsWith(".pdf")
                && !file.getOriginalFilename().endsWith(".PDF"))
        {
            return "Please upload PDF's only.";
        }
        try
        {
            String saveFileName = String
                    .format("%s_%s_%s_%s.pdf", queryId, clientId, siteId, documentType);

            Path root = Paths.get(getUploadParentPath());

            Path resolve = root.resolve(saveFileName);
            if (resolve.toFile()
                    .exists())
            {
                resolve.toFile().delete();
            }
            Files.copy(file.getInputStream(), resolve);
            updateQuery(queryId);
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
    public void getFile(
            HttpServletRequest request,
            HttpServletResponse response,
            Integer clientId,
            Integer siteId,
            Integer queryId,
            String documentType)
    {

        if (clientId == null || siteId == null || !StringUtils.hasText(documentType))
        {
            return;
        }
        try
        {
            String savedFileName = String
                    .format("%s_%s_%s_%s.pdf", queryId, clientId, siteId, documentType);

            Path root = Paths.get(getUploadParentPath());

            Path resolve = root.resolve(savedFileName);
            if (resolve.toFile()
                    .exists())
            {
                FileUtility.updateResponseWithFilePath(response, resolve.toFile().getPath());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public Client uploadProfilePic(TransportFile transportFile)
    {
        MultipartFile file = transportFile.getFile();
        Integer clientId = transportFile.getClientId();
        if (file == null || clientId == null)
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
        // 1. Get Client
        // 2. Get existing image if exists
        // 3. Save new image.
        // 4. Attach new Image to client
        // 5. Delete if image already existed.
        ClientEntity clientEntity = clientRepository.getById(clientId);
        Integer existingImageId = clientEntity.getClientImageMasterEntity() == null ? null
                : clientEntity.getClientImageMasterEntity().getImageId();

        ClientImageMasterEntity clientImageMasterEntity = new ClientImageMasterEntity(clientId, profilePic);
        ClientImageMasterEntity savedImage = clientImageMasterRepository.save(clientImageMasterEntity);
        clientEntity.setClientImageMasterEntity(savedImage);
        ClientEntity savedEntity = clientRepository.save(clientEntity);

        if (existingImageId != null)
        {
            clientImageMasterRepository.deleteById(existingImageId);
        }

        return Converter.getClientFromClientEntity(savedEntity);
    }

}
