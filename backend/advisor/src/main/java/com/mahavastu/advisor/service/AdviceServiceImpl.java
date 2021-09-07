package com.mahavastu.advisor.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
import com.mahavastu.advisor.model.AdvisorLoginDetails;
import com.mahavastu.advisor.model.LevelEnum;
import com.mahavastu.advisor.model.RequestResult;
import com.mahavastu.advisor.model.Site;
import com.mahavastu.advisor.model.UserQuery;
import com.mahavastu.advisor.repository.AdviceRepository;
import com.mahavastu.advisor.repository.AdvisorAppMetadataRepositroy;
import com.mahavastu.advisor.repository.AdvisorRepository;
import com.mahavastu.advisor.repository.ClientRepository;
import com.mahavastu.advisor.repository.SiteRepository;
import com.mahavastu.advisor.repository.UserQueryRepository;
import com.mahavastu.advisor.utility.FileUtility;
import com.mahavastu.advisor.utility.PdfFillUtility;

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
    public RequestResult advice(List<Advice> advices, Integer advisorId)
    {
        String message = "";
        Optional<AdvisorEntity> advisorEntityOptional = advisorId == null ? null : advisorRepository.findById(advisorId);
        if (CollectionUtils.isEmpty(advices) || advices.iterator().next() == null)
        {
            message = "Advice is not be updated! Please try again!";
        }
        else if(advisorEntityOptional == null || !advisorEntityOptional.isPresent())
        {
            message = "Advice could not be updated! Advisor not found. Please try again!";
        }
        else
        {
            Advice firstAdvice = advices.iterator().next();
            UserQueryEntity userQueryEntity = userQueryRepository.getById(firstAdvice.getUserQuery().getQueryId());
            SiteEntity siteEntity = siteRepository.getById(firstAdvice.getUserQuery().getSiteId());
            ClientEntity clientEntity = clientRepository.getById(firstAdvice.getUserQuery().getClient().getClientId());
            AdvisorEntity advisorEntity = advisorEntityOptional.get();
            
            List<AdviceEntity> adviceEntities = Converter
                    .getAdviceEntitiesFromAdvices(advices, userQueryEntity, siteEntity, clientEntity, advisorEntity);

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
                                + firstSavedEntity.getSiteQueryCompositeKey().getSiteEntity().getAddressEntity().getAddress());
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

        UserQuery userQuery = Converter.getUserQueryFromUserQueryEntity(userQueryEntity);
        Site site = Converter.getSiteFromSiteEntity(userQueryEntity.getSite());
        String reportGeneratedPath = reportFilePath.replace(".pdf", "-generated.pdf");
        try
        {
            PdfReader reader = new PdfReader(reportFilePath);

            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(reportGeneratedPath));
            AcroFields form = stamper.getAcroFields();

            // Generic Details
            PdfFillUtility.fillGenericDetails(userQuery, site, form);
            
            // Client Info
            PdfFillUtility.fillClientInfo(userQuery, form);

            // Site Info
            PdfFillUtility.fillSiteInfo(site, form);

            // Advice
            fillAllAdvices(advices, form);

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
        return reportGeneratedPath;

    }

    private void fillAllAdvices(List<Advice> advices, AcroFields form)
    {
        // Intuitive Audit
        List<Advice> intuitiveAudits = advices.stream().filter(advice -> advice.getLevel().equals(LevelEnum.LEVEL_1_K_INTUITIVE))
                .collect(Collectors.toList());
        PdfFillUtility.fillIntuitiveAudit(intuitiveAudits, form);

        // Entrance Audit
        List<Advice> entranceAudits = advices.stream().filter(advice -> advice.getLevel().equals(LevelEnum.LEVEL_1_A_ENTRANCE))
                .collect(Collectors.toList());
        PdfFillUtility.fillEntranceAudit(entranceAudits, form);

        // Prakriti Audit
        List<Advice> prakritiAudits = advices.stream()
                .filter(advice -> advice.getLevel().equals(LevelEnum.LEVEL_1_B_PRAKRITI_OF_PERSON))
                .collect(Collectors.toList());
        PdfFillUtility.fillPrakritiAudit(prakritiAudits, form);

        // Prakriti-Building Audit
        List<Advice> prakritiBuildingAudits = advices.stream()
                .filter(advice -> advice.getLevel().equals(LevelEnum.LEVEL_1_B_PRAKRITI_BUILDING))
                .collect(Collectors.toList());
        PdfFillUtility.fillPrakritiBuildingAudit(prakritiBuildingAudits, form);

        // Prakriti-Balancing Audit
        List<Advice> prakritiBalancingAudits = advices.stream()
                .filter(advice -> advice.getLevel().equals(LevelEnum.LEVEL_1_B_SUGGESTION_FOR_PRAKRITI))
                .collect(Collectors.toList());
        PdfFillUtility.fillPrakritiBalancingAudit(prakritiBalancingAudits, form);
        
        // Disha Bal Audit
        List<Advice> dishaBalAudits = advices.stream()
                .filter(advice -> advice.getLevel().equals(LevelEnum.LEVEL_1_C_DISHA_BAL))
                .collect(Collectors.toList());
        PdfFillUtility.fillDishaBalBalancingAudit(dishaBalAudits, form);
        
        // Five Elements Audit
        List<Advice> fiveAudits = advices.stream()
                .filter(advice -> advice.getLevel().equals(LevelEnum.LEVEL_1_D_FIVE_ELEMENTS))
                .collect(Collectors.toList());
        PdfFillUtility.fillFiveElementsAudit(fiveAudits, form);
        
        // Activity Audit
        List<Advice> activityAudits = advices.stream()
                .filter(advice -> advice.getLevel().equals(LevelEnum.LEVEL_1_E_ACTIVITY))
                .collect(Collectors.toList());
        PdfFillUtility.fillActivityAudit(activityAudits, form);
        
        // Utility Audit
        List<Advice> utilityAudits = advices.stream()
                .filter(advice -> advice.getLevel().equals(LevelEnum.LEVEL_1_F_UTILITY))
                .collect(Collectors.toList());
        PdfFillUtility.fillUtilityAudit(utilityAudits, form);
        
        // Objects Audit
        List<Advice> objectAudits = advices.stream()
                .filter(advice -> advice.getLevel().equals(LevelEnum.LEVEL_1_G_OBJECTS))
                .collect(Collectors.toList());
        PdfFillUtility.fillObjectAudit(objectAudits, form);
        
        // Remedies Audit
        List<Advice> remediesAudits = advices.stream()
                .filter(advice -> advice.getLevel().equals(LevelEnum.LEVEL_1_H_REMEDIES))
                .collect(Collectors.toList());
        PdfFillUtility.fillRemediesAudit(remediesAudits, form);  
        
        // Astro Audit - 1
        List<Advice> astroOneAudits = advices.stream()
                .filter(advice -> advice.getLevel().equals(LevelEnum.LEVEL_1_I_ASTRO_AUDIT_1))
                .collect(Collectors.toList());
        PdfFillUtility.fillAstroOneAudit(astroOneAudits, form);
        
        // Astro Audit - 2
        List<Advice> astroTwoAudits = advices.stream()
                .filter(advice -> advice.getLevel().equals(LevelEnum.LEVEL_1_I_ASTRO_AUDIT_2))
                .collect(Collectors.toList());
        // Resolve Zone issues
        // TODO OJASVI PdfFillUtility.fillAstroTwoAudit(astroTwoAudits, form);
        
        // Astro Remedies Audit - 3
        List<Advice> astroRemediesThreeAudits = advices.stream()
                .filter(advice -> advice.getLevel().equals(LevelEnum.LEVEL_1_I_ASTRO_AUDIT_3))
                .collect(Collectors.toList());
        PdfFillUtility.fillAstroRemediesThreeAudit(astroRemediesThreeAudits, form);
        
        // Marma Audit
        List<Advice> marmaAudits = advices.stream()
                .filter(advice -> advice.getLevel().equals(LevelEnum.LEVEL_1_J_MARMA))
                .collect(Collectors.toList());
        PdfFillUtility.fillMarmaAudit(marmaAudits, form);
        
    }

    private String copyTemplatesForRequest(List<Advice> advices)
    {
        String templateFileAddress = advisorAppMetadataRepositroy.findByPropertyKey("TEMPLATE_FILE").getPropertyValue();
        String templateDestDirectory = String.format(
                "%s/%s-%s",
                templateFileAddress.substring(0, templateFileAddress.lastIndexOf("/")),
                advices.iterator().next().getUserQuery().getQueryId(),
                UUID.randomUUID().toString());

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
    public Advisor login(AdvisorLoginDetails advisorLoginDetails)
    {
        if (advisorLoginDetails == null)
        {
            return null;
        }

        AdvisorEntity advisorEntity = advisorRepository.findByAdvisorEmailOrAdvisorMobile(advisorLoginDetails.getAdvisorId(), advisorLoginDetails.getAdvisorId());
        if (advisorEntity != null && advisorEntity.getPassword().equals(advisorLoginDetails.getPassword()))
        {
            return Converter.getAdvisorFromAdvisorEntity(advisorEntity);
        }
        return null;
    }
}
