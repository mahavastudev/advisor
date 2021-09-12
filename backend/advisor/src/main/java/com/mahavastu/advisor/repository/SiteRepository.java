package com.mahavastu.advisor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mahavastu.advisor.entity.ClientEntity;
import com.mahavastu.advisor.entity.SiteEntity;

@Repository
public interface SiteRepository extends JpaRepository<SiteEntity, Integer>, JpaSpecificationExecutor<SiteEntity> {
    List<SiteEntity> findByClient(@Param("cl_id") ClientEntity clientEntity);
}


