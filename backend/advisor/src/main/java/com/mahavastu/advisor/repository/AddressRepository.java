package com.mahavastu.advisor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mahavastu.advisor.entity.AddressEntity;

public interface AddressRepository extends JpaRepository<AddressEntity, Integer>
{

}
