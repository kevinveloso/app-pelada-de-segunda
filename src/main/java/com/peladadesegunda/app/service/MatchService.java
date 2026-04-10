package com.peladadesegunda.app.service;

import com.peladadesegunda.app.dto.MatchDto;

import java.util.List;

public interface MatchService {

    List<MatchDto> getAllMatches();

    MatchDto getMatch(Long id);

    MatchDto createMatch(MatchDto match);

    MatchDto updateMatch(Long id, MatchDto match);

    void deleteMatch(Long id);

    MatchDto addPlayerToMatch(Long matchId, String playerUsername);

    MatchDto removePlayerFromMatch(Long matchId, String playerUsername);
}
