package com.mahavastu.advisor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mahavastu.advisor.entity.AdvisorAppMetaDataEntity;

public interface AdvisorAppMetadataRepositroy extends JpaRepository<AdvisorAppMetaDataEntity, Integer>
{
    AdvisorAppMetaDataEntity findByPropertyKey(String propertyKey);
}
