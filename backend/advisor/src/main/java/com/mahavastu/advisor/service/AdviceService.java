package com.mahavastu.advisor.service;

import java.util.List;

import com.mahavastu.advisor.model.Advice;
import com.mahavastu.advisor.model.LevelEnum;
import com.mahavastu.advisor.model.RequestResult;

public interface AdviceService {
    RequestResult advice(List<Advice> advices);
    List<Advice> getAdvices(Integer queryId, Integer siteId, LevelEnum level);
}
