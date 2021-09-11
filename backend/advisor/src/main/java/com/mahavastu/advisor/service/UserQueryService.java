package com.mahavastu.advisor.service;

import com.mahavastu.advisor.model.UserQuery;

import java.util.List;
import java.util.Map;

public interface UserQueryService {
    UserQuery addUserQuery(UserQuery userQuery);
    List<UserQuery> getUserQueriesByClientId(Integer clientId);
    UserQuery getQueryById(Integer userQueryId);
    List<UserQuery> getAllActiveQueries();
    List<UserQuery> getUserQueriesBySiteId(int siteId);
    String resolveQueryByQueryId(int userQueryId, String resolveText, int advisorId);
    Map<String, Long> getBasicQueryStats();
}
