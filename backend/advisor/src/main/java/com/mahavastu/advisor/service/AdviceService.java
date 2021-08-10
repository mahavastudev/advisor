package com.mahavastu.advisor.service;

import com.mahavastu.advisor.model.Advice;
import com.mahavastu.advisor.model.LevelEnum;
import com.mahavastu.advisor.model.RequestResult;

import java.util.List;

public interface AdviceService {
    RequestResult advice(List<Advice> advices);
    List<Advice> getAdvices(Integer queryId, Integer siteId, LevelEnum level);
}
