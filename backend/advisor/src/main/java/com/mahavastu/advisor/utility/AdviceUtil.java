package com.mahavastu.advisor.utility;

import java.util.HashMap;
import java.util.Map;

import com.mahavastu.advisor.model.LevelEnum;

public class AdviceUtil
{
    private AdviceUtil()
    {
        // Empty Constructor
    }

    private static Map<LevelEnum, String> LEVEL_ENUM_TO_DEFINITION_MAP = new HashMap<>();

    static
    {
        LEVEL_ENUM_TO_DEFINITION_MAP.put(LevelEnum.LEVEL_1_A_ENTRANCE, "Entrance Audit");
        LEVEL_ENUM_TO_DEFINITION_MAP.put(LevelEnum.LEVEL_1_B_PRAKRITI_BUILDING, "Building Prakriti Audit");
        LEVEL_ENUM_TO_DEFINITION_MAP.put(LevelEnum.LEVEL_1_B_PRAKRITI_OF_PERSON, "Prakriti Audit");
        LEVEL_ENUM_TO_DEFINITION_MAP.put(LevelEnum.LEVEL_1_B_SUGGESTION_FOR_PRAKRITI, "Prakriti Balancing Audit");
        LEVEL_ENUM_TO_DEFINITION_MAP.put(LevelEnum.LEVEL_1_C_DISHA_BAL, "Disha Bal Audit");
        LEVEL_ENUM_TO_DEFINITION_MAP.put(LevelEnum.LEVEL_1_D_FIVE_ELEMENTS, "Five Elements Audit");
        LEVEL_ENUM_TO_DEFINITION_MAP.put(LevelEnum.LEVEL_1_E_ACTIVITY, "Activity Audit");
        LEVEL_ENUM_TO_DEFINITION_MAP.put(LevelEnum.LEVEL_1_F_UTILITY, "Utility Audit");
        LEVEL_ENUM_TO_DEFINITION_MAP.put(LevelEnum.LEVEL_1_G_OBJECTS, "Objects Audit");
        LEVEL_ENUM_TO_DEFINITION_MAP.put(LevelEnum.LEVEL_1_H_REMEDIES, "Remedies");
        LEVEL_ENUM_TO_DEFINITION_MAP.put(LevelEnum.LEVEL_1_I_ASTRO_AUDIT_1, "Astro Audit – 1");
        LEVEL_ENUM_TO_DEFINITION_MAP.put(LevelEnum.LEVEL_1_I_ASTRO_AUDIT_2, "Astro Audit – 2");
        LEVEL_ENUM_TO_DEFINITION_MAP.put(LevelEnum.LEVEL_1_I_ASTRO_AUDIT_3, "Astro Remedies – 3");
        LEVEL_ENUM_TO_DEFINITION_MAP.put(LevelEnum.LEVEL_1_J_MARMA, "Marma Audit");
        LEVEL_ENUM_TO_DEFINITION_MAP.put(LevelEnum.LEVEL_1_J_DEVTA_2, "Devta Audit – 2");
        LEVEL_ENUM_TO_DEFINITION_MAP.put(LevelEnum.LEVEL_1_K_INTUITIVE, "Intuitive Audit");
        LEVEL_ENUM_TO_DEFINITION_MAP.put(LevelEnum.LEVEL_1_K_DEVTA_1, "Devta Audit – 1");
    }

    public static String getAnalysisFromLevelEnum(LevelEnum level)
    {
        return level == null ? "Advice" : LEVEL_ENUM_TO_DEFINITION_MAP.get(level);
    }

}
