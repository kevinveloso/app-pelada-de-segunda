package com.peladadesegunda.app.mapper;

import com.peladadesegunda.app.dto.UserDto;
import com.peladadesegunda.app.enumeration.Position;
import com.peladadesegunda.app.enumeration.UserRole;
import com.peladadesegunda.app.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")

public abstract class AbstractUserMapper {

    @Mapping(target = "positionSet", source = "positionList")
    public abstract UserEntity toUserEntity(UserDto userDto);

    protected abstract Set<Position> toPositionSet(List<Position> positionList);

    public abstract List<UserDto> toUserDtoList(List<UserEntity> userEntityList);

    @Mapping(target = "positionList", source = "positionSet")
    public abstract UserDto toUserDto(UserEntity userEntity);

    protected abstract List<Position> toPositionList(Set<Position> positionSet);

    protected UserRole mapRole(UserRole role) {
        return role != null ? role : UserRole.PLAYER;
    }
}
