package com.mahavastu.advisor.entity.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.mahavastu.advisor.entity.AdvisorEntity;
import com.mahavastu.advisor.entity.ClientEntity;
import com.mahavastu.advisor.entity.MasterConcernEntity;
import com.mahavastu.advisor.entity.OccupationEntity;
import com.mahavastu.advisor.entity.SiteEntity;
import com.mahavastu.advisor.entity.SiteTypeEntity;
import com.mahavastu.advisor.entity.UserQueryEntity;
import com.mahavastu.advisor.entity.advice.AdviceEntity;
import com.mahavastu.advisor.entity.advice.SiteQueryCompositeKey;
import com.mahavastu.advisor.model.Advice;
import com.mahavastu.advisor.model.Advisor;
import com.mahavastu.advisor.model.Client;
import com.mahavastu.advisor.model.MasterConcern;
import com.mahavastu.advisor.model.Occupation;
import com.mahavastu.advisor.model.Site;
import com.mahavastu.advisor.model.SiteType;
import com.mahavastu.advisor.model.UserQuery;

public final class Converter
{

    public Converter()
    {
        // Empty Constructor as this is a Util Class.
    }

    public static SiteEntity buildSiteEntityFromSite(Site site, ClientEntity clientEntity, SiteTypeEntity siteTypeEntity)
    {
        if (site == null || clientEntity == null || siteTypeEntity == null)
        {
            return null;
        }
        return new SiteEntity(
                site.getSiteName(),
                site.getSiteAddress(),
                site.getSiteGeo(),
                siteTypeEntity,
                site.getSiteMapId(),
                clientEntity,
                site.getConditionType());
    }

    public static SiteEntity getSiteEntityFromSite(Site site, ClientEntity clientEntity, SiteTypeEntity siteTypeEntity)
    {
        if (site == null || clientEntity == null)
        {
            return null;
        }
        return new SiteEntity(
                site.getSiteId(),
                site.getSiteName(),
                site.getSiteAddress(),
                site.getSiteGeo(),
                siteTypeEntity,
                site.getSiteMapId(),
                clientEntity,
                site.getConditionType());
    }

    public static SiteTypeEntity getSiteTypeEntityFromSiteType(SiteType siteType)
    {
        return new SiteTypeEntity(siteType.getSiteTypeId(), siteType.getSiteTypeName());
    }

    public static Client getClientFromClientEntity(ClientEntity clientEntity)
    {
        if (clientEntity == null)
        {
            return null;
        }
        return new Client(
                clientEntity.getClientId(),
                clientEntity.getClientName(),
                clientEntity.getClientMobile(),
                clientEntity.getClientEmail(),
                clientEntity.getClientImageMasterEntity() == null ? null
                        : clientEntity.getClientImageMasterEntity().getClientDisplayPic(),
                clientEntity.getClientPOC(),
                new Occupation(clientEntity.getOccupation().getOccupationId(), clientEntity.getOccupation().getOccupationName()),
                null);
    }

    public static List<Site> getSitesFromSiteEntities(List<SiteEntity> siteEntities)
    {
        List<Site> sites = new ArrayList<>();
        if (CollectionUtils.isEmpty(siteEntities))
        {
            return sites;
        }
        siteEntities.stream().forEach(siteEntity -> {
            Site site = getSiteFromSiteEntity(siteEntity);
            if (site != null)
            {
                sites.add(site);
            }
        });
        return sites;
    }

    public static Site getSiteFromSiteEntity(SiteEntity siteEntity)
    {
        if (siteEntity == null)
        {
            return null;
        }
        return new Site(
                siteEntity.getSiteId(),
                siteEntity.getSiteName(),
                siteEntity.getSiteAddress(),
                siteEntity.getSiteGeo(),
                getSiteTypeFromSiteTypeEntity(siteEntity.getSiteType()),
                siteEntity.getSiteMapId(),
                getClientFromClientEntity(siteEntity.getClient()),
                siteEntity.getConditionType());
    }

    public static UserQueryEntity getUserQueryEntityFromUserQuery(
            UserQuery userQuery,
            ClientEntity clientEntity,
            SiteEntity siteEntity,
            MasterConcernEntity masterConcernEntity,
            AdvisorEntity advisorEntity)
    {
        if (userQuery == null || clientEntity == null || siteEntity == null)
        {
            return null;
        }
        return new UserQueryEntity(
                clientEntity,
                userQuery.getQueryText(),
                userQuery.getQueryCreateDatetime(),
                userQuery.getQueryCreateDatetime(),
                siteEntity,
                userQuery.getHoroId(),
                masterConcernEntity,
                userQuery.getResolveText(),
                advisorEntity);
    }

    public static UserQuery getUserQueryFromUserQueryEntity(UserQueryEntity userQueryEntity)
    {
        if (userQueryEntity == null)
        {
            return null;
        }
        return new UserQuery(
                userQueryEntity.getQueryId(),
                getClientFromClientEntity(userQueryEntity.getClient()),
                userQueryEntity.getQueryText(),
                userQueryEntity.getQueryCreateDatetime(),
                userQueryEntity.getQueryUpdateDatetime(),
                userQueryEntity.getSite().getSiteId(),
                userQueryEntity.getHoroId(),
                getMasterConcernFromMasterConcernEntity(userQueryEntity.getMasterConcernEntity()),
                userQueryEntity.isActive(),
                userQueryEntity.getResolveText(),
                getAdvisorFromAdvisorEntity(userQueryEntity.getAdvisorEntity()));
    }

    public static MasterConcern getMasterConcernFromMasterConcernEntity(MasterConcernEntity masterConcernEntity)
    {
        if (masterConcernEntity == null)
        {
            return null;
        }
        return new MasterConcern(masterConcernEntity.getConcernId(), masterConcernEntity.getConcernName());
    }

    public static List<UserQuery> getUserQueriesFromUserQueryEntities(List<UserQueryEntity> userQueryEntities)
    {
        List<UserQuery> userQueries = new ArrayList<>();
        if (CollectionUtils.isEmpty(userQueryEntities))
        {
            return userQueries;
        }
        userQueryEntities.stream().forEach(userQueryEntity -> {
            UserQuery userQuery = getUserQueryFromUserQueryEntity(userQueryEntity);
            if (userQuery != null)
            {
                userQueries.add(userQuery);
            }
        });
        return userQueries;
    }

    public static ClientEntity getClientEntityFromClient(Client client, OccupationEntity occupationEntity)
    {
        if (client == null)
        {
            return null;
        }
        if (client.getClientId() != null)
        {
            return new ClientEntity(
                    client.getClientId(),
                    client.getClientName(),
                    client.getClientMobile(),
                    client.getClientEmail(),
                    null,
                    client.getClientPOC(),
                    occupationEntity,
                    client.getPassword());
        }
        return new ClientEntity(
                client.getClientName(),
                client.getClientMobile(),
                client.getClientEmail(),
                null,
                client.getClientPOC(),
                occupationEntity,
                client.getPassword());

    }

    public static List<MasterConcern> getMasterConcernFromMasterConcernEntities(List<MasterConcernEntity> masterConcernEntities)
    {
        List<MasterConcern> masterConcerns = new ArrayList<>();
        if (CollectionUtils.isEmpty(masterConcernEntities))
        {
            return masterConcerns;
        }
        masterConcernEntities.stream().forEach(masterConcernEntity -> {
            MasterConcern masterConcern = getMasterConcernFromMasterConcernEntity(masterConcernEntity);
            if (masterConcern != null)
            {
                masterConcerns.add(masterConcern);
            }
        });
        Collections.sort(masterConcerns, (o1, o2) -> o1.getConcernName().compareTo(o2.getConcernName()));
        return masterConcerns;
    }

    public static List<SiteType> getSiteTypesFromSiteTypeEntities(List<SiteTypeEntity> siteTypeEntities)
    {
        List<SiteType> siteTypes = new ArrayList<>();
        if (CollectionUtils.isEmpty(siteTypeEntities))
        {
            return siteTypes;
        }
        siteTypeEntities.stream().forEach(siteTypeEntity -> {
            SiteType siteType = getSiteTypeFromSiteTypeEntity(siteTypeEntity);
            if (siteType != null)
            {
                siteTypes.add(siteType);
            }
        });
        return siteTypes;
    }

    private static SiteType getSiteTypeFromSiteTypeEntity(SiteTypeEntity siteTypeEntity)
    {
        if (siteTypeEntity == null)
        {
            return null;
        }
        return new SiteType(siteTypeEntity.getSiteTypeId(), siteTypeEntity.getSiteTypeName());
    }

    public static List<Occupation> getOccupationsFromOccupationEntities(List<OccupationEntity> occupationEntities)
    {
        List<Occupation> occupations = new ArrayList<>();
        if (CollectionUtils.isEmpty(occupationEntities))
        {
            return occupations;
        }
        occupationEntities.stream().forEach(occupationEntity -> {
            Occupation occupation = getOccupationFromOccupationEntity(occupationEntity);
            if (occupation != null)
            {
                occupations.add(occupation);
            }
        });
        return occupations;
    }

    public static Occupation getOccupationFromOccupationEntity(OccupationEntity occupationEntity)
    {
        if (occupationEntity == null)
        {
            return null;
        }
        return new Occupation(occupationEntity.getOccupationId(), occupationEntity.getOccupationName());
    }

    public static AdviceEntity getAdviceEntityFromAdvice(
            Advice advice,
            UserQueryEntity userQueryEntity,
            SiteEntity siteEntity,
            ClientEntity clientEntity,
            AdvisorEntity advisorEntity)
    {
        if (advice == null || userQueryEntity == null || siteEntity == null || clientEntity == null)
        {
            return null;
        }
        return new AdviceEntity(
                new SiteQueryCompositeKey(siteEntity, userQueryEntity, advice.getLevel(), advice.getZone()),
                advice.getEvaluation(),
                advice.getSuggestions(),
                advice.getTypeOfEntrance(),
                advice.getEntrance(),
                advice.getPrakriti(),
                advice.getBuildingActivity(),
                advice.getActivity(),
                advice.getPersonsActivity(),
                advice.getPrakrtitSink(),
                advice.getDishabal(),
                advice.getStatus(),
                advice.getFiveElements(),
                advice.getUtility(),
                advice.getObjects(),
                advice.getMvRemedies(),
                advice.getBox(),
                advice.getSign(),
                advice.getLordPositioning(),
                advice.getInfluence(),
                advice.getBestApproach(),
                advice.getMvDirections(),
                advice.getPlanets(),
                advice.getPresentObject(),
                advice.getMarma(),
                advice.getOuterDevta32(),
                advice.getOuterDevta(),
                advice.getConcerns(),
                advice.getIntuitiveDiagnosis(),
                advice.getInner12Devta(),
                advisorEntity);
    }

    public static List<AdviceEntity> getAdviceEntitiesFromAdvices(
            List<Advice> advices,
            UserQueryEntity userQueryEntity,
            SiteEntity siteEntity,
            ClientEntity clientEntity,
            AdvisorEntity advisorEntity)
    {
        List<AdviceEntity> adviceEntities = new ArrayList<>();
        if (CollectionUtils.isEmpty(advices))
        {
            return adviceEntities;
        }
        advices.stream().forEach(advice -> {
            AdviceEntity adviceEntity = getAdviceEntityFromAdvice(advice, userQueryEntity, siteEntity, clientEntity, advisorEntity);
            if (adviceEntity != null)
            {
                adviceEntities.add(adviceEntity);
            }
        });
        return adviceEntities;
    }

    public static List<Advice> getAdvicesFromAdviceEntities(List<AdviceEntity> adviceEntities)
    {
        List<Advice> advices = new ArrayList<>();
        if (CollectionUtils.isEmpty(adviceEntities))
        {
            return advices;
        }
        adviceEntities.stream().forEach(adviceEntity -> {
            Advice advice = getAdviceFromAdviceEntity(adviceEntity);
            if (advice != null)
            {
                advices.add(advice);
            }
        });
        return advices;
    }

    public static Advice getAdviceFromAdviceEntity(AdviceEntity adviceEntity)
    {
        if (adviceEntity == null)
        {
            return null;
        }
        return new Advice(
                getUserQueryFromUserQueryEntity(adviceEntity.getSiteQueryCompositeKey().getUserQueryEntity()),
                getSiteFromSiteEntity(adviceEntity.getSiteQueryCompositeKey().getSiteEntity()),
                adviceEntity.getSiteQueryCompositeKey().getLevel(),
                adviceEntity.getSiteQueryCompositeKey().getZone(),
                adviceEntity.getEvaluation(),
                adviceEntity.getSuggestions(),
                adviceEntity.getTypeOfEntrance(),
                adviceEntity.getEntrance(),
                adviceEntity.getPrakriti(),
                adviceEntity.getBuildingActivity(),
                adviceEntity.getActivity(),
                adviceEntity.getPersonsActivity(),
                adviceEntity.getPrakrtitSink(),
                adviceEntity.getDishabal(),
                adviceEntity.getStatus(),
                adviceEntity.getFiveElements(),
                adviceEntity.getUtility(),
                adviceEntity.getObjects(),
                adviceEntity.getMvRemedies(),
                adviceEntity.getBox(),
                adviceEntity.getSign(),
                adviceEntity.getLordPositioning(),
                adviceEntity.getInfluence(),
                adviceEntity.getBestApproach(),
                adviceEntity.getMvDirections(),
                adviceEntity.getPlanets(),
                adviceEntity.getPresentObject(),
                adviceEntity.getMarma(),
                adviceEntity.getOuterDevta32(),
                adviceEntity.getOuterDevta(),
                adviceEntity.getConcerns(),
                adviceEntity.getIntuitiveDiagnosis(),
                adviceEntity.getInner12Devta(),
                getAdvisorFromAdvisorEntity(adviceEntity.getAdvisorEntity()));
    }

    public static Advisor getAdvisorFromAdvisorEntity(AdvisorEntity advisorEntity)
    {
        if (advisorEntity == null)
        {
            return null;
        }
        return new Advisor(
                advisorEntity.getAdvisorId(),
                advisorEntity.getAdvisorName(),
                advisorEntity.getAdvisorMobile(),
                advisorEntity.getAdvisorEmail(),
                null);
    }


}
