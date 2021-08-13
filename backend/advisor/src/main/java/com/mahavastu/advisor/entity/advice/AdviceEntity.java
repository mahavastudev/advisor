package com.mahavastu.advisor.entity.advice;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "mv_advice")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdviceEntity
{

    @EmbeddedId
    private SiteQueryCompositeKey siteQueryCompositeKey;

    private String evaluation;
    private String suggestions;

    // Level 1 A - Entrance
    private String typeOfEntrance;
    private String entrance;

    // LEVEL_1_B_PRAKRITI_BUILDING
    private String prakriti;
    private String buildingActivity;

    // LEVEL_1_B_PRAKRITI_OF_PERSON
    private String activity;

    // LEVEL_1_B_SUGGESTION_FOR_PRAKRITI
    // private String prakriti;
    private String personsActivity;
    // private String buildingActivity;
    private String prakrtitSink;

    // Level 1 B - Dishabal
    private Integer dishabal;
    private String status;

}
