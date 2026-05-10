package com.peladadesegunda.app.mapper;

import com.peladadesegunda.app.dto.*;
import com.peladadesegunda.app.enumeration.MatchStatus;
import com.peladadesegunda.app.model.MatchEntity;
import com.peladadesegunda.app.model.MatchPlayerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class AbstractMatchMapper {

    @Autowired
    protected AbstractPlayerMapper abstractPlayerMapper;

    public abstract List<MatchDto> toMatchDtoList(List<MatchEntity> matchEntityList);

    @Mappings({
            @Mapping(target = "matchStatus", expression = "java(this.getMatchStatus(matchEntity))"),
            @Mapping(target = "subscribedPlayerList", expression = "java(this.getSubscribedPlayersList(matchEntity))"),
            @Mapping(target = "waitingList", expression = "java(this.getWaitingList(matchEntity))")
    })
    public abstract MatchDto toMatchDto(MatchEntity matchEntity);;

    public abstract MatchEntity toMatchEntity(AddUpdateMatchDto addUpdateMatchDto);

    public abstract MatchEntity toMatchEntity(MatchDto matchDto);

    protected MatchStatus getMatchStatus(MatchEntity matchEntity) {
        Date now = new Date();

        if (now.before(matchEntity.getMatchStartDate())) {
            return MatchStatus.SCHEDULED;
        } else if (now.after(matchEntity.getMatchEndDate())) {
            return MatchStatus.FINISHED;
        }

        return MatchStatus.ONGOING;
    }

    protected List<PlayerDto> getSubscribedPlayersList(MatchEntity matchEntity) {
        final List<MatchPlayerEntity> matchPlayerEntityList = toMatchPlayerEntityList(matchEntity);


        if (matchPlayerEntityList.size() > matchEntity.getMaxPlayers()) {
            return this.abstractPlayerMapper.toPlayerDtoList(matchPlayerEntityList
                    .subList(0, matchEntity.getMaxPlayers()).stream().map(MatchPlayerEntity::getPlayer).toList());
        }

        return this.abstractPlayerMapper.toPlayerDtoList(matchPlayerEntityList.stream().map(MatchPlayerEntity::getPlayer).toList());
    }

    protected List<PlayerDto> getWaitingList(MatchEntity matchEntity) {
        final List<MatchPlayerEntity> matchPlayerEntityList = toMatchPlayerEntityList(matchEntity);

        if (matchPlayerEntityList.size() > matchEntity.getMaxPlayers()) {
            return this.abstractPlayerMapper.toPlayerDtoList(matchPlayerEntityList
                    .subList(matchEntity.getMaxPlayers(), matchPlayerEntityList.size()).stream().map(MatchPlayerEntity::getPlayer).toList());
        }

        return new ArrayList<>();
    }

    private static List<MatchPlayerEntity> toMatchPlayerEntityList(MatchEntity matchEntity) {
        if (matchEntity.getMatchPlayerSet().isEmpty()) return new ArrayList<>();

        final List<MatchPlayerEntity> matchPlayerEntityList =  new ArrayList<>(matchEntity.getMatchPlayerSet());

        matchPlayerEntityList.sort(Comparator.comparing(MatchPlayerEntity::getSubscriptionDate));
        return matchPlayerEntityList;
    }


    public abstract List<MatchFromPlayerDto> toMatchFromPlayerDtoList(List<MatchPlayerEntity> matchPlayerEntityList);
    protected abstract MatchFromPlayerDto toMatchFromPlayerDto(MatchPlayerEntity matchPlayerEntity);
}
