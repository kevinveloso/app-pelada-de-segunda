package com.peladadesegunda.app.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AddUpdateMatchDto {
    public Long id;
    public Date matchDate;
    public Integer maxPlayers;
}
