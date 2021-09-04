package com.mahavastu.advisor.repository;

import com.mahavastu.advisor.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Integer> {
    ClientEntity findByClientEmailOrClientMobile(String email, String mobile);
}
