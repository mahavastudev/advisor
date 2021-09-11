package com.mahavastu.advisor.model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdviceMetadata
{
    private String analysis;
    private Advisor advisor;
    private Timestamp adviceUpdateDatetime;
}
