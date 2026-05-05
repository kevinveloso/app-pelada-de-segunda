package com.peladadesegunda.app.dto;

import lombok.Data;

import java.util.List;

@Data
public class PerformanceEvaluationDto {
    private MatchDto matchDto;
    private PlayerDto evaluator;
    private List<PlayerDto> evaluateeList;
}
