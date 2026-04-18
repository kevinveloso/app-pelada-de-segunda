package com.peladadesegunda.app.dto;

import com.peladadesegunda.app.enumeration.Position;
import com.peladadesegunda.app.enumeration.UserRole;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserDto {

    public Long id;
    public String username;
    private String password;
    public String name;
    public String nickname;
    public Date birthdate;
    public List<Position> positionList;
    public UserRole role;
    public Double gradeAverage;
    public Boolean regularMember;
    public Double lastMatchGrade;
}
