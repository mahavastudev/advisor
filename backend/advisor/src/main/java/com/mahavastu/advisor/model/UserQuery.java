package com.mahavastu.advisor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserQuery {
    private Integer queryId;
    private Client client;
    private String queryText;
    private Timestamp queryCreateDatetime;
    private Timestamp queryUpdateDatetime;
    private Integer siteId;
    private Integer horoId;
    private MasterConcern masterConcern;
}
