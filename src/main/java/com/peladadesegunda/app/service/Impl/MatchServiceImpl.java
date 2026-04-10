package com.peladadesegunda.app.service.Impl;

import com.peladadesegunda.app.dto.MatchDto;
import com.peladadesegunda.app.service.MatchService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatchServiceImpl implements MatchService {

    @Override
    public List<MatchDto> getAllMatches() {
        return new ArrayList<>();
    }

    @Override
    public MatchDto getMatch(Long id) {
        return null;
    }

    @Override
    public MatchDto createMatch(MatchDto match) {
        return null;
    }

    @Override
    public MatchDto updateMatch(Long id, MatchDto match) {
        return null;
    }

    @Override
    public void deleteMatch(Long id) {

    }

    @Override
    public MatchDto addPlayerToMatch(Long matchId, String playerUsername) {
        return null;
    }

    @Override
    public MatchDto removePlayerFromMatch(Long matchId, String playerUsername) {
        return null;
    }
}
