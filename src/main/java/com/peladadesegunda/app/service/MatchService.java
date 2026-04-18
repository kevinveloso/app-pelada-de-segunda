package com.peladadesegunda.app.service;

import com.peladadesegunda.app.dto.MatchDto;
import com.peladadesegunda.app.exception.MatchNotFoundException;
import com.peladadesegunda.app.exception.UserNotFoundException;

import java.util.List;

public interface MatchService {

    List<MatchDto> getAllMatches();

    MatchDto getMatch(Long id) throws MatchNotFoundException;

    MatchDto createMatch(MatchDto match);

    MatchDto updateMatch(MatchDto match);

    void deleteMatch(Long id);

    MatchDto addPlayerToMatch(Long matchId, String playerUsername) throws UserNotFoundException, MatchNotFoundException;

    MatchDto removePlayerFromMatch(Long matchId, String playerUsername);
}
