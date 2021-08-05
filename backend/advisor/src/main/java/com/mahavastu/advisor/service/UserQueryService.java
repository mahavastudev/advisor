package com.mahavastu.advisor.service;

import com.mahavastu.advisor.model.UserQuery;

import java.util.List;

public interface UserQueryService {
    UserQuery addUserQuery(UserQuery userQuery);
    List<UserQuery> getUserQueriesByClientId(Integer clientId);
    UserQuery getQueryById(Integer userQueryId);
}
