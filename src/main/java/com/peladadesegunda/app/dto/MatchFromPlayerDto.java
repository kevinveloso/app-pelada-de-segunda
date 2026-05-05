package com.peladadesegunda.app.dto;

import lombok.Data;

@Data
public class MatchFromPlayerDto {
    private Long id;
    private MatchDto match;
    private Integer team;
    private Integer goalsScored;
    private Boolean wasPresent;
}
