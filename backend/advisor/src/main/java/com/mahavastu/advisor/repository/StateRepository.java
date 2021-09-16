package com.mahavastu.advisor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mahavastu.advisor.entity.StateEntity;

@Repository
public interface StateRepository extends JpaRepository<StateEntity, Integer>
{
    List<StateEntity> findByCounCode(String counCode);
}
