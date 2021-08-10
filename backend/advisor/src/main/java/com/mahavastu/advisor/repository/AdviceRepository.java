package com.mahavastu.advisor.repository;

import com.mahavastu.advisor.entity.advice.AdviceEntity;
import com.mahavastu.advisor.entity.advice.SiteQueryCompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdviceRepository extends JpaRepository<AdviceEntity, SiteQueryCompositeKey> {

    @Query("SELECT a FROM AdviceEntity a WHERE a.siteQueryCompositeKey.userQueryEntity.queryId = :queryId AND a.siteQueryCompositeKey.siteEntity.siteId = :siteId AND a.siteQueryCompositeKey.level = :level")
    List<AdviceEntity> getAdvicesForQuerySiteAndLevel(
            @Param("queryId") Integer queryId,
            @Param("siteId") Integer siteId,
            @Param("level") String level);

}
