package com.peladadesegunda.app.dto;

import lombok.Data;

import java.util.List;

@Data
public class MatchResultDto {
    private Long matchId;
    private Integer teamAScore;
    private Integer teamBScore;
    private List<PlayerDto> teamAScorers;
    private List<PlayerDto> teamBScorers;
    private Double teamAAverageNote;
    private Double teamBAverageNote;
}
