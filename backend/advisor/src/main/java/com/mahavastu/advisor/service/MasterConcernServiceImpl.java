package com.mahavastu.advisor.service;

import com.mahavastu.advisor.entity.converter.Converter;
import com.mahavastu.advisor.model.MasterConcern;
import com.mahavastu.advisor.repository.MasterConcernRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MasterConcernServiceImpl implements MasterConcernService{

    @Autowired
    private MasterConcernRepository masterConcernRepository;

    @Override
    public List<MasterConcern> getAllMasterConcerns() {
        return Converter.getMasterConcernFromMasterConcernEntities(masterConcernRepository.findAll());
    }
}
