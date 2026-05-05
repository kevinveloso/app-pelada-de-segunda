package com.peladadesegunda.app.mapper;

import com.peladadesegunda.app.dto.PlayerDto;
import com.peladadesegunda.app.dto.UserDto;
import com.peladadesegunda.app.model.PlayerEntity;
import com.peladadesegunda.app.model.UserEntity;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class AbstractUserMapper {

    @Autowired
    protected AbstractPlayerMapper abstractPlayerMapper;

    public abstract UserEntity toUserEntity(UserDto userDto);

    protected PlayerEntity toPlayerEntity(PlayerDto playerDto) {
        return this.abstractPlayerMapper.toPlayerEntity(playerDto);
    }

    public abstract List<UserDto> toUserDtoList(List<UserEntity> userEntityList);

    public abstract UserDto toUserDto(UserEntity userEntity);

    protected PlayerDto toPlayerDto(PlayerEntity playerEntity) {
        return this.abstractPlayerMapper.toPlayerDto(playerEntity);
    }
}
