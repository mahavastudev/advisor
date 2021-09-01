package com.mahavastu.advisor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mahavastu.advisor.entity.AdvisorEntity;

public interface AdvisorRepository extends JpaRepository<AdvisorEntity, Integer>
{

}
