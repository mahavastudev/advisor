package com.mahavastu.advisor.repository;

import com.mahavastu.advisor.entity.MasterConcernEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterConcernRepository extends JpaRepository<MasterConcernEntity, Integer> {

}
