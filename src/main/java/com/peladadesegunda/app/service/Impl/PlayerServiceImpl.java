package com.peladadesegunda.app.service.Impl;

import com.peladadesegunda.app.dto.PerformanceEvaluationDto;
import com.peladadesegunda.app.dto.PlayerDto;
import com.peladadesegunda.app.exception.PlayerNotFoundException;
import com.peladadesegunda.app.mapper.AbstractMatchMapper;
import com.peladadesegunda.app.mapper.AbstractPlayerMapper;
import com.peladadesegunda.app.model.PlayerEntity;
import com.peladadesegunda.app.repository.MatchPlayerRepository;
import com.peladadesegunda.app.repository.PlayerRepository;
import com.peladadesegunda.app.service.PlayerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final MatchPlayerRepository matchPlayerRepository;

    private final AbstractPlayerMapper playerMapper;
    private final AbstractMatchMapper matchMapper;

    public PlayerServiceImpl(PlayerRepository playerRepository, MatchPlayerRepository matchPlayerRepository,
                             AbstractPlayerMapper playerMapper, AbstractMatchMapper matchMapper) {
        this.playerRepository = playerRepository;
        this.matchPlayerRepository = matchPlayerRepository;
        this.playerMapper = playerMapper;
        this.matchMapper = matchMapper;
    }

    @Override
    public List<PlayerDto> getAllPlayers(Pageable pageable) {
        Page<PlayerEntity> playerEntityList = this.playerRepository.findAll(pageable);

        if (playerEntityList.isEmpty()) {
            return new ArrayList<>();
        }

        return this.playerMapper.toPlayerDtoList(playerEntityList.stream().toList());
    }

    @Override
    public PlayerDto getPlayer(Long id) throws PlayerNotFoundException {
        Optional<PlayerEntity> playerEntityOptional = this.playerRepository.findById(id);

        if (playerEntityOptional.isEmpty()) {
            throw new PlayerNotFoundException(String.valueOf(id));
        }

        return this.playerMapper.toPlayerDto(playerEntityOptional.get());
    }

    @Override
    public PlayerDto createPlayer(PlayerDto player) {
        PlayerEntity playerEntity = this.playerMapper.toPlayerEntity(player);

        PlayerEntity savedPlayerEntity = this.playerRepository.save(playerEntity);

        return this.playerMapper.toPlayerDto(savedPlayerEntity);
    }

    @Override
    public PlayerDto updatePlayer(PlayerDto player) throws PlayerNotFoundException {
        Objects.requireNonNull(player.getId(), "ID can't be null!");

        Optional<PlayerEntity> playerEntityOptional = this.playerRepository.findById(player.getId());
        if (playerEntityOptional.isEmpty()) throw new PlayerNotFoundException(String.valueOf(player.getId()));

        PlayerEntity playerEntity = changePlayerEntity(player, playerEntityOptional.get());

        PlayerEntity updatedPlayerEntity = this.playerRepository.save(playerEntity);

        return this.playerMapper.toPlayerDto(updatedPlayerEntity);
    }

    @Override
    public void deletePlayer(Long id) {
        this.playerRepository.deleteById(id);
    }

    @Override
    public void evaluatePerformances(PerformanceEvaluationDto performanceEvaluationDto) {

    }

    @Override
    public List<PlayerDto> getAllRegularMembers() {
        List<PlayerEntity> playerEntityList = this.playerRepository.findByRegularMemberTrueOrderByNameAsc();

        if (playerEntityList.isEmpty()) {
            return new ArrayList<>();
        }

        return this.playerMapper.toPlayerDtoList(playerEntityList.stream().toList());
    }

    @Override
    public List<PlayerDto> getAllAvailablePlayerFromMatch(Long matchId, Pageable pageable) {
        Objects.requireNonNull(matchId, "Match ID can't be null!");

        Page<PlayerEntity> players = this.playerRepository.findAllAvailableFromMatch(matchId, pageable);
        if (players.isEmpty()) {
            return List.of();
        }

        return this.playerMapper.toPlayerDtoList(players.stream().toList());
    }


    private static PlayerEntity changePlayerEntity(PlayerDto player, PlayerEntity playerEntityOptional) {

        if (Objects.nonNull(player.getName())) playerEntityOptional.setName(player.getName());
        if (Objects.nonNull(player.getNickname())) playerEntityOptional.setNickname(player.getNickname());
        if (Objects.nonNull(player.getBirthdate())) playerEntityOptional.setBirthdate(player.getBirthdate());
        if (!player.getPositionList().isEmpty()) playerEntityOptional.setPositionSet(new HashSet<>(player.getPositionList()));
        if (Objects.nonNull(player.getRegularMember())) playerEntityOptional.setRegularMember(player.getRegularMember());

        return playerEntityOptional;
    }
}
