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

    MatchDto addPlayerToMatch(Long matchId, String playerUsername) throws UserNotFoundException, MatchNotFoundException, PlayerAlreadyInMatchException;

    List<MatchFromUserDto> getMatchesFromUser(String username, Pageable pageable) throws UserNotFoundException;

    void removePlayerFromMatch(Long matchId, String playerUsername) throws MatchNotFoundException, UserNotInMatchException;

    TeamsDto drawTeams(DrawTeamsDto drawTeamsDto) throws MatchNotFoundException, MatchIsOverException;
}
