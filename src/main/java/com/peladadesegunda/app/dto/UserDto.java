package com.peladadesegunda.app.dto;

import com.peladadesegunda.app.enumeration.Position;
import com.peladadesegunda.app.enumeration.UserRole;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserDto {

    private Long id;
    private String username;
    private String password;
    private String name;
    private String nickname;
    private Date birthdate;
    private List<Position> positionList;
    private UserRole role;
    private Double gradeAverage;
    private Boolean regularMember;
    private Double lastMatchGrade;
}
