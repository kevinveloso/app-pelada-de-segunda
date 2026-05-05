package com.peladadesegunda.app.service;

import com.peladadesegunda.app.dto.PerformanceEvaluationDto;
import com.peladadesegunda.app.dto.PlayerDto;
import com.peladadesegunda.app.exception.PlayerNotFoundException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PlayerService {
    List<PlayerDto> getAllPlayers(Pageable pageable);

    PlayerDto getPlayer(Long id) throws PlayerNotFoundException;

    PlayerDto createPlayer(PlayerDto player);

    PlayerDto updatePlayer(PlayerDto player) throws PlayerNotFoundException;

    void deletePlayer(Long id);

    void evaluatePerformances(PerformanceEvaluationDto performanceEvaluationDto);

    List<PlayerDto> getAllRegularMembers();
}
