package com.mahavastu.advisor.utility;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.mahavastu.advisor.model.Advice;
import com.mahavastu.advisor.model.Site;
import com.mahavastu.advisor.model.UserQuery;

public class PdfFillUtility
{
    private static SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd-MM-yyyy");
    
    public static void fillClientInfo(UserQuery userQuery, AcroFields form) throws IOException, DocumentException
    {
        form.setField("#QUERY_OWNER_NAME#", userQuery.getClient().getClientName());
        form.setField("#QUERY_OWNER_MOBILE#", userQuery.getClient().getClientMobile());
        form.setField("#QUERY_OWNER_DATE#", DATE_FORMATTER.format(userQuery.getQueryCreateDatetime()));
        form.setField("#QUERY_OWNER_EMAIL#", userQuery.getClient().getClientEmail());
        
        // Only if the query is resolved.
        if(userQuery.getAdvisor() != null)
        {
            form.setField("#QUERY_OWNER_POINT_OF_CONTACT#", userQuery.getAdvisor().getAdvisorName());
            form.setField("#QUERY_OWNER_POINT_OF_CONTACT_MOBILE#", userQuery.getAdvisor().getAdvisorMobile());
        }
        
    }

    public static void fillSiteInfo(Site site, AcroFields form) throws IOException, DocumentException
    {
        form.setField("#SITE_INFO_CONSTRUCTION#", site.getSiteType().getSiteTypeName());
        form.setField("#SITE_INFO_TYPE#", site.getConditionType());
        form.setField("#SITE_INFO_LOCATION#", site.getSiteAddress());
        form.setField("#SITE_INFO_GOOGLE_LOCATION#", site.getSiteGeo());
        form.setField("#SITE_INFO_ADDRESS#", site.getSiteAddress());
        
    }

    public static void fillEntranceAudit(List<Advice> entranceAudits, AcroFields form)
    {
        entranceAudits.stream().forEach(advice -> {
            try
            {
                form.setField("#" + advice.getLevel() + "_" + advice.getZone() + "_" + "ENTRANCE_TYPE#", advice.getTypeOfEntrance());
                form.setField("#" + advice.getLevel() + "_" + advice.getZone() + "_" + "EFFECTS#", advice.getEvaluation());
                form.setField("#" + advice.getLevel() + "_" + advice.getZone() + "_" + "MAHAVASTU_ADVICE#", advice.getSuggestions());
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            catch (DocumentException e)
            {
                e.printStackTrace();
            }
        });
    }

    public static void fillPrakritiAudit(List<Advice> prakritiAudits, AcroFields form)
    {
        prakritiAudits.stream().forEach(advice -> {
            try
            {
                form.setField("#" + advice.getLevel() + "_" + advice.getZone() + "_" + "ACTIVITY#", advice.getActivity());
                form.setField("#" + advice.getLevel() + "_" + advice.getZone() + "_" + "ADVICE#", advice.getSuggestions());
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            catch (DocumentException e)
            {
                e.printStackTrace();
            }
        });
    }

    public static void fillPrakritiBuildingAudit(List<Advice> prakritiBuildingAudits, AcroFields form)
    {
        prakritiBuildingAudits.stream().forEach(advice -> {
            try
            {
                // #LEVEL_1_B_SUGGESTION_FOR_PRAKRITI_NE_MOTIVATION_PERSON_ACTIVITY#
                form.setField("#" + advice.getLevel() + "_" + advice.getZone() + "_" + "ACTIVITY#", advice.getActivity());
                form.setField("#" + advice.getLevel() + "_" + advice.getZone() + "_" + "ADVICE#", advice.getSuggestions());
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            catch (DocumentException e)
            {
                e.printStackTrace();
            }
        });
    }

    public static void fillIntuitiveAudit(List<Advice> intuitiveAudits, AcroFields form)
    {
        int currentAdviceIndex = 1;
        for(Advice advice : intuitiveAudits)
        {
            try
            {
                // #LEVEL_1_K_INTUITIVE_CONCERN_L1#
                form.setField("#" + advice.getLevel() + "_CONCERN_L" + currentAdviceIndex + "#", advice.getConcerns());
                // #LEVEL_1_K_INTUITIVE_SUGGESTION_L1#
                form.setField("#" + advice.getLevel() + "_SUGGESTION_L" + currentAdviceIndex + "#", advice.getSuggestions());
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            catch (DocumentException e)
            {
                e.printStackTrace();
            }
            currentAdviceIndex++;
        }
    }

    public static void fillPrakritiBalancingAudit(List<Advice> prakritiBalancingAudits, AcroFields form)
    {
        prakritiBalancingAudits.stream().forEach(advice -> {
            try
            {
                // #LEVEL_1_B_SUGGESTION_FOR_PRAKRITI_NE_MOTIVATION_PERSON_ACTIVITY#
                form.setField("#" + advice.getLevel() + "_" + advice.getZone() + "_" + "PERSON_ACTIVITY#", advice.getPersonsActivity());
                // #LEVEL_1_B_SUGGESTION_FOR_PRAKRITI_NE_MOTIVATION_BUILDING_ACTIVITY#
                form.setField("#" + advice.getLevel() + "_" + advice.getZone() + "_" + "BUILDING_ACTIVITY#", advice.getBuildingActivity());
                // #LEVEL_1_B_SUGGESTION_FOR_PRAKRITI_NE_MOTIVATION_SINK#
                form.setField("#" + advice.getLevel() + "_" + advice.getZone() + "_" + "SINK#", advice.getPrakrtitSink());
                // #LEVEL_1_B_SUGGESTION_FOR_PRAKRITI_NE_MOTIVATION_SUGGESTION#
                form.setField("#" + advice.getLevel() + "_" + advice.getZone() + "_" + "SUGGESTION#", advice.getSuggestions());
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            catch (DocumentException e)
            {
                e.printStackTrace();
            }
        });
    }

}
