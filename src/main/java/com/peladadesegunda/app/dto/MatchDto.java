package com.peladadesegunda.app.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MatchDto {
    public Long id;
    public Date matchDate;
    public Integer maxPlayers;
    public List<UserDto> subscribedPlayerList;
    public List<UserDto> waitingList;
}
