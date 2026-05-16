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
    private Integer minPlayers;
    private Integer maxPlayers;
    private String place;
    private String extraInfo;
    private MatchStatus matchStatus;
    private List<PlayerDto> subscribedPlayerList;
    private List<PlayerDto> waitingList;
}
