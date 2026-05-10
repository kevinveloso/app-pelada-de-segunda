package com.peladadesegunda.app.mapper;

import com.peladadesegunda.app.dto.PlayerDto;
import com.peladadesegunda.app.enumeration.Position;
import com.peladadesegunda.app.model.PlayerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public abstract class AbstractPlayerMapper {

    @Mapping(target = "positionSet", source = "positionList")
    public abstract PlayerEntity toPlayerEntity(PlayerDto playerDto);

    protected abstract Set<Position> toPositionSet(List<Position> positionList);

    public abstract List<PlayerDto> toPlayerDtoList(List<PlayerEntity> playerEntityList);

    @Mapping(target = "positionList", source = "positionSet")
    public abstract PlayerDto toPlayerDto(PlayerEntity playerEntity);

    protected abstract List<Position> toPositionList(Set<Position> positionSet);
}
