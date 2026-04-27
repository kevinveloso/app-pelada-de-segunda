package com.peladadesegunda.app.service;

import com.peladadesegunda.app.dto.AddUpdateMatchDto;
import com.peladadesegunda.app.dto.MatchDto;
import com.peladadesegunda.app.exception.MatchNotFoundException;
import com.peladadesegunda.app.exception.PlayerAlreadyInMatchException;
import com.peladadesegunda.app.exception.UserNotFoundException;
import com.peladadesegunda.app.exception.UserNotInMatchException;

import java.util.List;

public interface MatchService {

    List<MatchDto> getAllMatches();

    MatchDto getMatch(Long id) throws MatchNotFoundException;

    MatchDto createMatch(AddUpdateMatchDto match);

    MatchDto updateMatch(AddUpdateMatchDto match) throws MatchNotFoundException;

    void deleteMatch(Long id);

    MatchDto addPlayerToMatch(Long matchId, String playerUsername) throws UserNotFoundException, MatchNotFoundException, PlayerAlreadyInMatchException;

    void removePlayerFromMatch(Long matchId, String playerUsername) throws MatchNotFoundException, UserNotInMatchException;
}
