package com.mahavastu.advisor.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Advice
{

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

    // LEVEL_1_D_FIVE_ELEMENTS
    private String fiveElements;

    // LEVEL_1_E_ACTIVITY
    //    private String activity;

    // LEVEL_1_F_UTILITY
    private String utility;

    // LEVEL_1_G_OBJECTS
    private String objects;

    // LEVEL_1_H_REMEDIES
    private String mvRemedies;

    // LEVEL_1_I_ASTRO_1
    private String box;
    private String sign;
    private String lordPositioning;
    private String influence;
    private String bestApproach;

    // LEVEL_1_I_ASTRO_2
    private String mvDirections;
    private String planets;
    //    private String influence;
    private String presentObject;

    // LEVEL_1_I_ASTRO_3
    //    private String activity;
    //    private String utility;

    // LEVEL_1_J_MARMA
    private String marma;

    // LEVEL_1_J_DEVTA_2
    private String outerDevta32;
    private String outerDevta;

    // LEVEL_1_K_INTUITIVE
    private String concerns;
    private String intuitiveDiagnosis;

    // LEVEL_1_K_DEVTA_1
    private String inner12Devta;

}
