package com.peladadesegunda.app.dto;

import com.peladadesegunda.app.enumeration.UserRole;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private UserRole role;
    private PlayerDto player;
}
