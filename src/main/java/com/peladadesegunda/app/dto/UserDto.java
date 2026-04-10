package com.peladadesegunda.app.dto;

import com.peladadesegunda.app.dto.enumeration.Position;
import com.peladadesegunda.app.dto.enumeration.UserRole;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserDto {

    public String username;
    public String name;
    public String nickname;
    public Date birthdate;
    public List<Position> positionList;
    public UserRole userRole;
    public Double gradeAverage;
    public Boolean regularMember;
    public Double lastMatchGrade;
}
