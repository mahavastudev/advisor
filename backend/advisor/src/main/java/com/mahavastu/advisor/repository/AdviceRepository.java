package com.mahavastu.advisor.repository;

import com.mahavastu.advisor.entity.advice.AdviceEntity;
import com.mahavastu.advisor.entity.advice.SiteQueryCompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdviceRepository extends JpaRepository<AdviceEntity, SiteQueryCompositeKey> {

//    @Query("SELECT * FROM mv_advice a WHERE a.query_id = ?1 AND a.site_id = ?2 AND a.level =?3")
//    List<AdviceEntity> getAdvicesForQuerySiteAndLevel(Integer queryId, Integer siteId, String level);

}
