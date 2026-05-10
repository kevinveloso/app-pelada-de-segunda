package com.peladadesegunda.app.dto;

import com.peladadesegunda.app.enumeration.Position;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PlayerDto {
    private Long id;
    private String name;
    private String nickname;
    private Date birthdate;
    private Boolean regularMember;
    private List<Position> positionList;
    private Double gradeAverage;
    private Double lastMatchGrade;
}
