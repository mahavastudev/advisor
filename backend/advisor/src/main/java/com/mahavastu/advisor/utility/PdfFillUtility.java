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
        form.setField("#SITE_INFO_LOCATION#", site.getAddress().getAddress());
        form.setField("#SITE_INFO_GOOGLE_LOCATION#", site.getAddress().getSiteGeo());
        form.setField("#SITE_INFO_ADDRESS#", site.getAddress().getAddress());
        
    }

    public static void fillEntranceAudit(List<Advice> entranceAudits, AcroFields form)
    {
        entranceAudits.stream().forEach(advice -> {
            try
            {
                // #LEVEL_1_A_ENTRANCE_NE_ENTRANCE_TYPE#
                form.setField("#" + advice.getLevel() + "_" + advice.getZone() + "_ENTRANCE_TYPE#", advice.getTypeOfEntrance());
                // LEVEL_1_A_ENTRANCE_NE_EFFECTS#
                // form.setField("#" + advice.getLevel() + "_" + advice.getZone() + "_EFFECTS#", advice.getEvaluation());
                form.setField(advice.getLevel() + "_" + advice.getZone() + "_EFFECTS#", advice.getEvaluation());
                // LEVEL_1_A_ENTRANCE_NE_MAHAVASTU_ADVICE#
                // form.setField("#" + advice.getLevel() + "_" + advice.getZone() + "_MAHAVASTU_ADVICE#", advice.getSuggestions());
                form.setField(advice.getLevel() + "_" + advice.getZone() + "_MAHAVASTU_ADVICE#", advice.getSuggestions());
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
                // #LEVEL_1_B_PRAKRITI_BUILDING_NE_BUILDING_ACTIVITY#
                form.setField("#" + advice.getLevel() + "_" + advice.getZone() + "_BUILDING_ACTIVITY#", advice.getBuildingActivity());
                // #LEVEL_1_B_PRAKRITI_BUILDING_NE_SUGGESTION#
                form.setField("#" + advice.getLevel() + "_" + advice.getZone() + "_SUGGESTION#", advice.getSuggestions());
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

    public static void fillDishaBalBalancingAudit(List<Advice> dishaBalAudits, AcroFields form)
    {
        dishaBalAudits.stream().forEach(advice -> {
            try
            {
                // #LEVEL_1_C_DISHA_BAL_NE_DISHABAL#
                String dishaBal = advice.getDishabal() == null ? "" : advice.getDishabal().toString();
                form.setField("#" + advice.getLevel() + "_" + advice.getZone() + "_DISHABAL#", dishaBal);
                // #LEVEL_1_C_DISHA_BAL_NE_STATUS#
                form.setField("#" + advice.getLevel() + "_" + advice.getZone() + "_STATUS#", advice.getStatus());
                // #LEVEL_1_C_DISHA_BAL_NE_EVALUATION#
                form.setField("#" + advice.getLevel() + "_" + advice.getZone() + "_EVALUATION#", advice.getEvaluation());
                // #LEVEL_1_C_DISHA_BAL_NE_SUGGESTION#
                form.setField("#" + advice.getLevel() + "_" + advice.getZone() + "_SUGGESTION#", advice.getSuggestions());
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

    public static void fillFiveElementsAudit(List<Advice> fiveAudits, AcroFields form)
    {
        // #LEVEL_1_D_FIVE_ELEMENTS_NNE_SUGGESTION#  
        fiveAudits.stream().forEach(advice -> {
            try
            {
                // #LEVEL_1_D_FIVE_ELEMENTS_NE_FIVE_ELEMENT_OBJECT#
                form.setField("#" + advice.getLevel() + "_" + advice.getZone() + "_FIVE_ELEMENT_OBJECT#", advice.getFiveElements());
                // #LEVEL_1_C_DISHA_BAL_NE_EVALUATION#
                form.setField("#" + advice.getLevel() + "_" + advice.getZone() + "_EVALUATION#", advice.getEvaluation());
                // #LEVEL_1_C_DISHA_BAL_NE_SUGGESTION#
                form.setField("#" + advice.getLevel() + "_" + advice.getZone() + "_SUGGESTION#", advice.getSuggestions());
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

    public static void fillActivityAudit(List<Advice> activityAudits, AcroFields form)
    {
        activityAudits.stream().forEach(advice -> {
            try
            {
                // #LEVEL_1_E_ACTIVITY_NE_ACTIVITY#
                form.setField("#" + advice.getLevel() + "_" + advice.getZone() + "_ACTIVITY#", advice.getActivity());
                // #LEVEL_1_E_ACTIVITY_NE_EVALUATION#
                form.setField("#" + advice.getLevel() + "_" + advice.getZone() + "_EVALUATION#", advice.getEvaluation());
                // #LEVEL_1_E_ACTIVITY_NE_SUGGESTION#
                form.setField("#" + advice.getLevel() + "_" + advice.getZone() + "_SUGGESTION#", advice.getSuggestions());
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

    public static void fillUtilityAudit(List<Advice> utilityAudits, AcroFields form)
    {
        utilityAudits.stream().forEach(advice -> {
            try
            {
                // #LEVEL_1_F_UTILITY_NE_ACTIVITY#
                form.setField("#" + advice.getLevel() + "_" + advice.getZone() + "_ACTIVITY#", advice.getUtility());
                // #LEVEL_1_F_UTILITY_NE_EVALUATION#
                form.setField("#" + advice.getLevel() + "_" + advice.getZone() + "_EVALUATION#", advice.getEvaluation());
                // #LEVEL_1_F_UTILITY_NE_SUGGESTION#
                form.setField("#" + advice.getLevel() + "_" + advice.getZone() + "_SUGGESTION#", advice.getSuggestions());
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

    public static void fillObjectAudit(List<Advice> objectAudits, AcroFields form)
    {
        objectAudits.stream().forEach(advice -> {
            try
            {
                // #LEVEL_1_G_OBJECTS_NE_OBJECT#
                form.setField("#" + advice.getLevel() + "_" + advice.getZone() + "_OBJECT#", advice.getObjects());
                // #LEVEL_1_G_OBJECTS_NE_EVALUATION#
                form.setField("#" + advice.getLevel() + "_" + advice.getZone() + "_EVALUATION#", advice.getEvaluation());
                // #LEVEL_1_G_OBJECTS_NE_SUGGESTION#
                form.setField("#" + advice.getLevel() + "_" + advice.getZone() + "_SUGGESTION#", advice.getSuggestions());
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

    public static void fillRemediesAudit(List<Advice> remediesAudits, AcroFields form)
    {
        remediesAudits.stream().forEach(advice -> {
            try
            {
                // #LEVEL_1_G_OBJECTS_NE_EVALUATION#
                form.setField("#" + advice.getLevel() + "_" + advice.getZone() + "_EVALUATION#", advice.getEvaluation());
                // #LEVEL_1_H_REMEDIES_NE_MV_REMEDIES#
                form.setField("#" + advice.getLevel() + "_" + advice.getZone() + "_MV_REMEDIES#", advice.getMvRemedies());
                // #LEVEL_1_H_REMEDIES_NE_MV_SUGGESTIONS#
                form.setField("#" + advice.getLevel() + "_" + advice.getZone() + "_MV_SUGGESTIONS#", advice.getSuggestions());
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

    public static void fillAstroOneAudit(List<Advice> astroOneAudits, AcroFields form)
    {
        astroOneAudits.stream().forEach(advice -> {
            try
            {
                String zone = advice.getZone().toUpperCase().replaceAll(" ", "_");
                
                // #LEVEL_1_I_ASTRO_AUDIT_1_POWER_OF_MANIFESTATION_CUSP_SIGN#
                form.setField("#" + advice.getLevel() + "_" + zone + "_CUSP_SIGN#", advice.getSign());
                // #LEVEL_1_I_ASTRO_AUDIT_1_POWER_OF_MANIFESTATION_INFLUENCE#
                form.setField("#" + advice.getLevel() + "_" + zone + "_INFLUENCE#", advice.getInfluence());
                // #LEVEL_1_I_ASTRO_AUDIT_1_POWER_OF_MANIFESTATION_BEST_APPROACH#
                form.setField("#" + advice.getLevel() + "_" + zone + "_BEST_APPROACH#", advice.getSuggestions());
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

    public static void fillAstroTwoAudit(List<Advice> astroTwoAudits, AcroFields form)
    {
        astroTwoAudits.stream().forEach(advice -> {
            try
            {
                String zone = advice.getZone().toUpperCase().replaceAll(" ", "_");
                
                // #LEVEL_1_I_ASTRO_AUDIT_2_VENUS_CUSP_SIGN#
                form.setField("#" + advice.getLevel() + "_" + zone + "_CUSP_SIGN#", advice.getSign());
                // #LEVEL_1_I_ASTRO_AUDIT_1_POWER_OF_MANIFESTATION_INFLUENCE#
                form.setField("#" + advice.getLevel() + "_" + zone + "_MV_REMEDIES#", advice.getInfluence());
                // #LEVEL_1_I_ASTRO_AUDIT_1_POWER_OF_MANIFESTATION_BEST_APPROACH#
                form.setField("#" + advice.getLevel() + "_" + zone + "_SUGGESTIONS#", advice.getSuggestions());
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

    public static void fillAstroRemediesThreeAudit(List<Advice> astroRemediesThreeAudits, AcroFields form)
    {
        astroRemediesThreeAudits.stream().forEach(advice -> {
            try
            {
                String zone = advice.getZone().toUpperCase().replaceAll(" ", "_");
                
                // #LEVEL_1_I_ASTRO_AUDIT_3_VISION_MANIFESTATION_SIGN#
                form.setField("#" + advice.getLevel() + "_" + zone + "_SIGN#", advice.getSign());
                // #LEVEL_1_I_ASTRO_AUDIT_3_VISION_MANIFESTATION_MV_ZONE#
                form.setField("#" + advice.getLevel() + "_" + zone + "_MV_ZONE#", advice.getMvDirections());
                // #LEVEL_1_I_ASTRO_AUDIT_3_VISION_MANIFESTATION_ACTIVITY#
                form.setField("#" + advice.getLevel() + "_" + zone + "_ACTIVITY#", advice.getActivity());
                // #LEVEL_1_I_ASTRO_AUDIT_3_VISION_MANIFESTATION_UTILITY#
                form.setField("#" + advice.getLevel() + "_" + zone + "_UTILITY#", advice.getUtility());
                // #LEVEL_1_I_ASTRO_AUDIT_3_VISION_MANIFESTATION_EVALUATION#
                form.setField("#" + advice.getLevel() + "_" + zone + "_EVALUATION#", advice.getEvaluation());
                // #LEVEL_1_I_ASTRO_AUDIT_3_VISION_MANIFESTATION_SUGGESTION#
                form.setField("#" + advice.getLevel() + "_" + zone + "_SUGGESTION#", advice.getSuggestions());
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

    public static void fillMarmaAudit(List<Advice> marmaAudits, AcroFields form)
    {
        marmaAudits.stream().forEach(advice -> {
            try
            {
                // #LEVEL_1_J_MARMA_NE_MARMA#
                form.setField("#" + advice.getLevel() + "_" + advice.getZone() + "_MARMA#", advice.getMarma());
                // #LEVEL_1_G_OBJECTS_NE_EVALUATION#
                form.setField("#" + advice.getLevel() + "_" + advice.getZone() + "_EVALUATION#", advice.getEvaluation());
                // #LEVEL_1_G_OBJECTS_NE_SUGGESTION#
                form.setField("#" + advice.getLevel() + "_" + advice.getZone() + "_SUGGESTION#", advice.getSuggestions());
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

    public static void fillGenericDetails(UserQuery userQuery, Site site, AcroFields form)
    {
        try
        {
            if(userQuery != null && site != null)
            {
                form.setField("#CLIENT_SITE#", String.format("%s - %s - %s", userQuery.getClient().getClientName(), site.getSiteName(), userQuery.getMasterConcern().getConcernName()));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (DocumentException e)
        {
            e.printStackTrace();
        }
    }

}
