package com.mahavastu.advisor.entity.converter;

import com.mahavastu.advisor.entity.*;
import com.mahavastu.advisor.model.*;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public final class Converter {

    public Converter() {
        // Empty Constructor as this is a Util Class.
    }

    public static SiteEntity buildSiteEntityFromSite(Site site, ClientEntity clientEntity) {
        if (site == null || clientEntity == null) {
            return null;
        }
        return new SiteEntity(site.getSiteName(),
                site.getSiteAddress(),
                site.getSiteGeo(),
                site.getSiteTypeId(),
                site.getSiteMapId(),
                clientEntity,
                site.getConditionType());
    }

    public static SiteEntity getSiteEntityFromSite(Site site, ClientEntity clientEntity) {
        if (site == null || clientEntity == null) {
            return null;
        }
        return new SiteEntity(site.getSiteId(),
                site.getSiteName(),
                site.getSiteAddress(),
                site.getSiteGeo(),
                site.getSiteTypeId(),
                site.getSiteMapId(),
                clientEntity,
                site.getConditionType());
    }


    public static Client getClientFromClientEntity(ClientEntity clientEntity) {
        if (clientEntity == null) {
            return null;
        }
        return new Client(clientEntity.getClientId(),
                clientEntity.getClientName(),
                clientEntity.getClientMobile(),
                clientEntity.getClientEmail(),
                clientEntity.getClientDisplayPic(),
                clientEntity.getClientPOC(),
                new Occupation(clientEntity.getOccupation().getOccupationId(), clientEntity.getOccupation().getOccupationName()),
                null);
    }

    public static List<Site> getSitesFromSiteEntities(List<SiteEntity> siteEntities) {
        List<Site> sites = new ArrayList<>();
        if (CollectionUtils.isEmpty(siteEntities)) {
            return sites;
        }
        siteEntities.stream().forEach(siteEntity -> {
            Site site = getSiteFromSiteEntity(siteEntity);
            if (site != null) {
                sites.add(site);
            }
        });
        return sites;
    }

    public static Site getSiteFromSiteEntity(SiteEntity siteEntity) {
        if (siteEntity == null) {
            return null;
        }
        return new Site(siteEntity.getSiteId(),
                siteEntity.getSiteName(),
                siteEntity.getSiteAddress(),
                siteEntity.getSiteGeo(),
                siteEntity.getSiteTypeId(),
                siteEntity.getSiteMapId(),
                getClientFromClientEntity(siteEntity.getClient()),
                siteEntity.getConditionType());
    }

    public static UserQueryEntity getUserQueryEntityFromUserQuery(UserQuery userQuery, ClientEntity clientEntity, SiteEntity siteEntity, MasterConcernEntity masterConcernEntity) {
        if (userQuery == null || clientEntity == null || siteEntity == null) {
            return null;
        }
        return new UserQueryEntity(clientEntity, userQuery.getQueryText(), userQuery.getQueryCreateDatetime(), userQuery.getQueryCreateDatetime(), siteEntity, userQuery.getHoroId(), masterConcernEntity);
    }

    public static UserQuery getUserQueryFromUserQueryEntity(UserQueryEntity userQueryEntity) {
        if (userQueryEntity == null) {
            return null;
        }
        return new UserQuery(userQueryEntity.getQueryId(),
                getClientFromClientEntity(userQueryEntity.getClient()),
                userQueryEntity.getQueryText(),
                userQueryEntity.getQueryCreateDatetime(),
                userQueryEntity.getQueryUpdateDatetime(),
                userQueryEntity.getSite().getSiteId(),
                userQueryEntity.getHoroId(),
                getMasterConcernFromMasterConcernEntity(userQueryEntity.getMasterConcernEntity()));
    }

    public static MasterConcern getMasterConcernFromMasterConcernEntity(MasterConcernEntity masterConcernEntity) {
        if(masterConcernEntity == null) {
            return null;
        }
        return new MasterConcern(masterConcernEntity.getConcernId(), masterConcernEntity.getConcernName());
    }

    public static List<UserQuery> getUserQueriesFromUserQueryEntities(List<UserQueryEntity> userQueryEntities) {
        List<UserQuery> userQueries = new ArrayList<>();
        if (CollectionUtils.isEmpty(userQueryEntities)) {
            return userQueries;
        }
        userQueryEntities.stream().forEach(userQueryEntity -> {
            UserQuery userQuery = getUserQueryFromUserQueryEntity(userQueryEntity);
            if (userQuery != null) {
                userQueries.add(userQuery);
            }
        });
        return userQueries;
    }

    public static ClientEntity getClientEntityFromClient(Client client, OccupationEntity occupationEntity) {
        if (client == null) {
            return null;
        }
        return new ClientEntity(client.getClientName(),
                client.getClientMobile(),
                client.getClientEmail(),
                client.getClientDisplayPic(),
                client.getClientPOC(),
                occupationEntity,
                client.getPassword());

    }
}
