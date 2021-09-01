package com.mahavastu.advisor.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.mahavastu.advisor.entity.AdvisorEntity;
import com.mahavastu.advisor.entity.ClientEntity;
import com.mahavastu.advisor.entity.SiteEntity;
import com.mahavastu.advisor.entity.UserQueryEntity;
import com.mahavastu.advisor.entity.advice.AdviceEntity;
import com.mahavastu.advisor.entity.converter.Converter;
import com.mahavastu.advisor.model.Advice;
import com.mahavastu.advisor.model.Advisor;
import com.mahavastu.advisor.model.Client;
import com.mahavastu.advisor.model.LevelEnum;
import com.mahavastu.advisor.model.RequestResult;
import com.mahavastu.advisor.repository.AdviceRepository;
import com.mahavastu.advisor.repository.AdvisorAppMetadataRepositroy;
import com.mahavastu.advisor.repository.AdvisorRepository;
import com.mahavastu.advisor.repository.ClientRepository;
import com.mahavastu.advisor.repository.SiteRepository;
import com.mahavastu.advisor.repository.UserQueryRepository;
import com.mahavastu.advisor.utility.FileUtility;

@Service
public class AdviceServiceImpl implements AdviceService
{

    @Autowired
    private AdviceRepository adviceRepository;

    @Autowired
    private UserQueryRepository userQueryRepository;

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AdvisorAppMetadataRepositroy advisorAppMetadataRepositroy;
    
    @Autowired
    private AdvisorRepository advisorRepository;

    @Override
    public RequestResult advice(List<Advice> advices)
    {
        String message = "";
        if (CollectionUtils.isEmpty(advices) || advices.iterator().next() == null)
        {
            message = "Advice could not be updated! Please try again!";
        }
        else
        {
            Advice firstAdvice = advices.iterator().next();
            UserQueryEntity userQueryEntity = userQueryRepository.getById(firstAdvice.getUserQuery().getQueryId());
            SiteEntity siteEntity = siteRepository.getById(firstAdvice.getUserQuery().getSiteId());
            ClientEntity clientEntity = clientRepository.getById(firstAdvice.getUserQuery().getClient().getClientId());

            List<AdviceEntity> adviceEntities = Converter
                    .getAdviceEntitiesFromAdvices(advices, userQueryEntity, siteEntity, clientEntity);

            if (!CollectionUtils.isEmpty(adviceEntities))
            {
                List<AdviceEntity> savedEntities = adviceRepository.saveAll(adviceEntities);
                AdviceEntity firstSavedEntity = savedEntities.iterator().next();
                message = String.format(
                        "Advice updated for Query-Id: %s , Client : %s , Site : %s",
                        firstSavedEntity.getSiteQueryCompositeKey().getUserQueryEntity().getQueryId(),
                        firstSavedEntity.getSiteQueryCompositeKey().getUserQueryEntity().getClient().getClientName(),
                        firstSavedEntity.getSiteQueryCompositeKey().getSiteEntity().getSiteName()
                                + " - "
                                + firstSavedEntity.getSiteQueryCompositeKey().getSiteEntity().getSiteAddress());
            }
            else
            {
                message = "Advice could not be updated! Please try again!";
            }
        }
        return new RequestResult(message);
    }

    @Override
    public List<Advice> getAdvices(Integer queryId, Integer siteId, LevelEnum levelEnum)
    {
        List<AdviceEntity> adviceEntities = adviceRepository.getAdvicesForQuerySiteAndLevel(queryId, siteId, levelEnum);
        List<Advice> advices = Converter.getAdvicesFromAdviceEntities(adviceEntities);
        return advices;
    }

    @Override
    public void generateAdvicePdfForQuery(HttpServletResponse response, Integer queryId)
    {
        String filePath = createPdfAndGetFilePath(queryId);
        FileUtility.updateResponseWithFilePath(response, filePath);
    }

    private String createPdfAndGetFilePath(Integer queryId)
    {
        List<AdviceEntity> adviceEntities = adviceRepository.findBySiteQueryCompositeKeyUserQueryEntityQueryId(queryId);
        if (CollectionUtils.isEmpty(adviceEntities))
        {
            return null;
        }
        List<Advice> advices = Converter.getAdvicesFromAdviceEntities(adviceEntities);
        return createPdfAndGetFile(advices, queryId);
    }

    private String createPdfAndGetFile(List<Advice> advices, Integer queryId)
    {
        String reportFilePath = copyTemplatesForRequest(advices);
       
        UserQueryEntity userQueryEntity = userQueryRepository.getById(queryId);

        try
        {
            PdfReader reader = new PdfReader(reportFilePath);

            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(reportFilePath));
            AcroFields form = stamper.getAcroFields();

            String name = userQueryEntity.getClient().getClientName();
            form.setField("#QUERY_OWNER_NAME#", name);

            // General
            fillGeneralDetails(userQueryEntity, form);

            // Advice
            int row = 1;
            for (int adviceIndex = 0; adviceIndex < advices.size(); adviceIndex++)
            {
                Advice advice = advices.get(adviceIndex);
                form.setField("#L" + row + "C5#", advice.getLevel().toString());
                form.setField("#L" + row + "C2#", advice.getZone());
                form.setField("#L" + row + "C3#", advice.getEntrance());
                form.setField("#L" + row + "C4#", advice.getEvaluation());
                form.setField("#L" + row + "C1#", advice.getSuggestions());
                form.setField("#L" + row + "C6#", advice.getTypeOfEntrance());
                row += 2;
            }

            stamper.setFormFlattening(true);
            stamper.close();
            reader.close();

        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (DocumentException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return reportFilePath;

    }

    private void fillGeneralDetails(UserQueryEntity userQueryEntity, AcroFields form)
            throws IOException,
            DocumentException
    {
        String siteDetails = String
                .format("%s - %s", userQueryEntity.getSite().getSiteId(), userQueryEntity.getSite().getSiteName());
        form.setField("#QUERY_SITE_DETAILS#", siteDetails);

        form.setField("#QUERY_CONCERN#", userQueryEntity.getMasterConcernEntity().getConcernName());
    }

    private String copyTemplatesForRequest(List<Advice> advices)
    {
        String templateFileAddress = advisorAppMetadataRepositroy.findByPropertyKey("TEMPLATE_FILE").getPropertyValue();
        String templateDestDirectory = String.format(
                "%s/%s-%s",
                templateFileAddress.substring(0, templateFileAddress.lastIndexOf("/")),
                advices.iterator().next().getUserQuery().getQueryId(),
                Calendar.getInstance().getTimeInMillis());

        File directory = new File(templateDestDirectory);
        if (!directory.exists())
        {
            directory.mkdir();
        }
        String destFile = String.format("%s/%s.pdf", templateDestDirectory, "report");
        try
        {
            FileUtils.cleanDirectory(directory);

            Path srcTemplateBackup = Paths.get(templateFileAddress);
            Path destTemplateBackup = Paths.get(destFile);

            Files.copy(srcTemplateBackup, destTemplateBackup);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return destFile;
    }

    @Override
    public void generateAndSendAdvicePdfForQuery(HttpServletResponse response, Integer queryId)
    {
        // TODO OJASVI
        String filePath = createPdfAndGetFilePath(queryId);
        // send Mail and return response
    }

    @Override
    public Advisor login(Advisor advisor)
    {
        if (advisor == null)
        {
            return null;
        }
            
        AdvisorEntity advisorEntity = advisorRepository.getById(advisor.getAdvisorId());
        if (advisorEntity != null && advisorEntity.getPassword().equals(advisor.getPassword()))
        {
            return Converter.getAdvisorFromAdvisorEntity(advisorEntity);
        }
        return null;
    }
}
