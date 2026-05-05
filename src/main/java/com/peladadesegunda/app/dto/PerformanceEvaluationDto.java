package com.peladadesegunda.app.dto;

import lombok.Data;

import java.util.List;

@Data
public class PerformanceEvaluationDto {
    private MatchDto matchDto;
    private UserDto evaluator;
    private List<PlayerDto> evaluateeList;
}
