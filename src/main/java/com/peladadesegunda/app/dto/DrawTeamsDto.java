package com.peladadesegunda.app.dto;

import com.peladadesegunda.app.enumeration.DrawStyle;
import lombok.Data;

@Data
public class DrawTeamsDto {
    private Long matchId;
    private DrawStyle drawStyle;
}
