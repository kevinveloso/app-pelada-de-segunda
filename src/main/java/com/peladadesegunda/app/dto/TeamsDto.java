package com.peladadesegunda.app.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TeamsDto {

    private Long matchId;
    private List<UserDto> teamA;
    private List<UserDto> teamB;

    public TeamsDto() {
        this.teamA = new ArrayList<>();
        this.teamB = new ArrayList<>();
    }
}
