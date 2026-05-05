package com.peladadesegunda.app.dto;

import com.peladadesegunda.app.enumeration.MatchStatus;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MatchDto {
    private Long id;
    private Date matchStartDate;
    private Date matchEndDate;
    private Integer maxPlayers;
    private MatchStatus matchStatus;
    private List<UserDto> subscribedPlayerList;
    private List<UserDto> waitingList;
}
