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

    // Level 1 B - Dishabal
    private Integer dishabal;
    private String status;

}
