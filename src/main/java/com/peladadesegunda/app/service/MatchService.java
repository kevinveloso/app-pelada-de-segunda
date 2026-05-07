package com.peladadesegunda.app.service;

import com.peladadesegunda.app.dto.*;
import com.peladadesegunda.app.exception.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MatchService {

    List<MatchDto> getAllMatches(Pageable pageable);

    MatchDto getMatch(Long id) throws MatchNotFoundException;

    MatchDto createMatch(AddUpdateMatchDto match);

    MatchDto updateMatch(AddUpdateMatchDto match) throws MatchNotFoundException;

    void deleteMatch(Long id);

    MatchDto addPlayerToMatch(Long matchId, Long playerId) throws PlayerNotFoundException,
            MatchNotFoundException, PlayerAlreadyInMatchException;

    List<MatchFromPlayerDto> getMatchesFromPlayer(Long playerId, Pageable pageable) throws PlayerNotFoundException;

    void removePlayerFromMatch(Long matchId, Long playerId) throws MatchNotFoundException,
            PlayerNotInMatchException, PlayerNotFoundException;

    TeamsDto drawTeams(DrawTeamsDto drawTeamsDto) throws MatchNotFoundException, MatchIsOverException;

    MatchResultDto getMatchResult(Long matchId) throws MatchNotFoundException;
}
