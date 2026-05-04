package com.peladadesegunda.app.dto;

import com.peladadesegunda.app.enumeration.MatchStatus;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MatchDto {
    public Long id;
    public Date matchStartDate;
    public Date matchEndDate;
    public Integer maxPlayers;
    public MatchStatus matchStatus;
    public List<UserDto> subscribedPlayerList;
    public List<UserDto> waitingList;
}
