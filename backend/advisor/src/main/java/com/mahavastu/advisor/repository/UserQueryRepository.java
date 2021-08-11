package com.mahavastu.advisor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mahavastu.advisor.entity.ClientEntity;
import com.mahavastu.advisor.entity.SiteEntity;
import com.mahavastu.advisor.entity.UserQueryEntity;

@Repository
public interface UserQueryRepository extends JpaRepository<UserQueryEntity, Integer> {
    List<UserQueryEntity> findByClient(@Param("cl_id") ClientEntity clientEntity);
    List<UserQueryEntity> findBySite(@Param("site_id") SiteEntity siteEntity);
}
