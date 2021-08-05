package com.mahavastu.advisor.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "mv_query")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserQueryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer queryId;

    @OneToOne
    @JoinColumn(name = "cl_id")
    private ClientEntity client;

    private String queryText;
    private Timestamp queryCreateDatetime;
    private Timestamp queryUpdateDatetime;

    @OneToOne
    @JoinColumn(name = "site_id")
    private SiteEntity site;

    private Integer horoId;

    @OneToOne
    @JoinColumn(name = "concern_id")
    private MasterConcernEntity masterConcernEntity;

    public UserQueryEntity(ClientEntity client, String queryText, Timestamp queryCreateDateTime, Timestamp queryUpdateDateTime, SiteEntity site, Integer horoId, MasterConcernEntity masterConcernEntity) {
        this.client = client;
        this.queryText = queryText;
        this.queryCreateDatetime = queryCreateDateTime;
        this.queryUpdateDatetime = queryUpdateDateTime;
        this.site = site;
        this.horoId = horoId;
        this.masterConcernEntity = masterConcernEntity;
    }
}
