package com.peladadesegunda.app.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AddUpdateMatchDto {
    private Long id;
    private Date matchStartDate;
    private Date matchEndDate;
    private Integer maxPlayers;
}
