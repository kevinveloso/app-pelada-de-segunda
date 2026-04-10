package com.peladadesegunda.app.dto;

import lombok.Data;

import java.util.List;

@Data
public class PerformanceEvaluationDto {
    public MatchDto matchDto;
    public UserDto evaluator;
    public List<UserDto> evaluateeList;
}
