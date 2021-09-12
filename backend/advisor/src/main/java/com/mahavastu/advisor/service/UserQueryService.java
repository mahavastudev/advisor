package com.mahavastu.advisor.service;

import java.util.List;
import java.util.Map;

import com.mahavastu.advisor.model.SearchElement;
import com.mahavastu.advisor.model.UserQuery;

public interface UserQueryService {
    UserQuery addUserQuery(UserQuery userQuery);
    List<UserQuery> getUserQueriesByClientId(Integer clientId);
    UserQuery getQueryById(Integer userQueryId);
    List<UserQuery> getAllActiveQueries();
    List<UserQuery> getUserQueriesBySiteId(int siteId);
    String resolveQueryByQueryId(int userQueryId, String resolveText, int advisorId);
    Map<String, Long> getBasicQueryStats();
    List<UserQuery> getFilteredUserQueries(SearchElement searchElement);
}
