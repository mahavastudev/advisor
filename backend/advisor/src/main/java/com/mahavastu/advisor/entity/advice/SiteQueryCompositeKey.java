package com.mahavastu.advisor.entity.advice;

import com.mahavastu.advisor.entity.SiteEntity;
import com.mahavastu.advisor.entity.UserQueryEntity;
import com.mahavastu.advisor.model.LevelEnum;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SiteQueryCompositeKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @OneToOne
    @JoinColumn(name = "site_id")
    private SiteEntity siteEntity;

    @OneToOne
    @JoinColumn(name = "query_id")
    private UserQueryEntity userQueryEntity;

    @Enumerated(EnumType.STRING)
    private LevelEnum level;

    private String zone;
}
