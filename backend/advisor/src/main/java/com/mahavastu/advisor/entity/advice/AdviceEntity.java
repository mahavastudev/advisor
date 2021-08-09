package com.mahavastu.advisor.entity.advice;


import com.mahavastu.advisor.model.LevelEnum;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "mv_advice")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdviceEntity {

    @EmbeddedId
    private SiteQueryCompositeKey siteQueryCompositeKey;

    private String evaluation;
    private String suggestions;

    // Level 1 A - Entrance
    private String typeOfEntrance;
    private String entrance;

    // Level 1 B - Dishabal
    private Integer dishabal;
    private String status;

}
