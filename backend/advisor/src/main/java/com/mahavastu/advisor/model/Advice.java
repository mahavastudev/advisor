package com.mahavastu.advisor.model;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Advice {

    private UserQuery userQuery;
    private Site site;
    private LevelEnum level;

    private String zone;
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
