package com.peladadesegunda.app.mapper;

import com.peladadesegunda.app.dto.AddUpdateMatchDto;
import com.peladadesegunda.app.dto.MatchDto;
import com.peladadesegunda.app.dto.UserDto;
import com.peladadesegunda.app.enumeration.MatchStatus;
import com.peladadesegunda.app.model.MatchEntity;
import com.peladadesegunda.app.model.MatchPlayerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Mapper(componentModel = "spring")
public abstract class AbstractMatchMapper {

    @Autowired
    protected AbstractUserMapper abstractUserMapper;

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

    protected List<UserDto> getSubscribedPlayersList(MatchEntity matchEntity) {
        final List<MatchPlayerEntity> matchPlayerEntityList = toMatchPlayerEntityList(matchEntity);


        if (matchPlayerEntityList.size() > matchEntity.getMaxPlayers()) {
            return this.abstractUserMapper.toUserDtoList(matchPlayerEntityList
                    .subList(0, matchEntity.getMaxPlayers()).stream().map(MatchPlayerEntity::getUser).toList());
        }

        return this.abstractUserMapper.toUserDtoList(matchPlayerEntityList.stream().map(MatchPlayerEntity::getUser).toList());
    }

    protected List<UserDto> getWaitingList(MatchEntity matchEntity) {
        final List<MatchPlayerEntity> matchPlayerEntityList = toMatchPlayerEntityList(matchEntity);

        if (matchPlayerEntityList.size() > matchEntity.getMaxPlayers()) {
            return this.abstractUserMapper.toUserDtoList(matchPlayerEntityList
                    .subList(matchEntity.getMaxPlayers(), matchPlayerEntityList.size()).stream().map(MatchPlayerEntity::getUser).toList());
        }

        return new ArrayList<>();
    }

    private static List<MatchPlayerEntity> toMatchPlayerEntityList(MatchEntity matchEntity) {
        if (matchEntity.getMatchPlayerSet().isEmpty()) return new ArrayList<>();

        final List<MatchPlayerEntity> matchPlayerEntityList =  new ArrayList<>(matchEntity.getMatchPlayerSet());

        matchPlayerEntityList.sort(Comparator.comparing(MatchPlayerEntity::getSubscriptionDate));
        return matchPlayerEntityList;
    }
}
