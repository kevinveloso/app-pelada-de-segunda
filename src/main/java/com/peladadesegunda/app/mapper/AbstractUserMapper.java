package com.peladadesegunda.app.mapper;

import com.peladadesegunda.app.dto.PlayerDto;
import com.peladadesegunda.app.dto.UserDto;
import com.peladadesegunda.app.enumeration.Position;
import com.peladadesegunda.app.model.UserEntity;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class AbstractUserMapper {
    @Mapping(target = "positionSet", source = "positionList")
    public abstract UserEntity toUserEntity(UserDto userDto);

    protected PlayerEntity toPlayerEntity(PlayerDto playerDto) {
        return this.abstractPlayerMapper.toPlayerEntity(playerDto);
    }

    public abstract List<UserDto> toUserDtoList(List<UserEntity> userEntityList);

    public abstract UserDto toUserDto(UserEntity userEntity);

    protected abstract List<Position> toPositionList(Set<Position> positionSet);
}
