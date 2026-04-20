package com.peladadesegunda.app.service.Impl;

import com.peladadesegunda.app.dto.MatchDto;
import com.peladadesegunda.app.exception.MatchNotFoundException;
import com.peladadesegunda.app.exception.PlayerAlreadyInMatchException;
import com.peladadesegunda.app.exception.UserNotFoundException;
import com.peladadesegunda.app.exception.UserNotInMatchException;
import com.peladadesegunda.app.mapper.AbstractMatchMapper;
import com.peladadesegunda.app.model.MatchEntity;
import com.peladadesegunda.app.model.MatchPlayerEntity;
import com.peladadesegunda.app.model.UserEntity;
import com.peladadesegunda.app.repository.MatchPlayerRepository;
import com.peladadesegunda.app.repository.MatchRepository;
import com.peladadesegunda.app.repository.UserRepository;
import com.peladadesegunda.app.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MatchServiceImpl implements MatchService {

    @Autowired
    private AbstractMatchMapper matchMapper;

    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private MatchPlayerRepository matchPlayerRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<MatchDto> getAllMatches() {
        List<MatchEntity> matchEntityList = this.matchRepository.findAll();

        if (matchEntityList.isEmpty()) {
            return new ArrayList<>();
        }

        return this.matchMapper.toMatchDtoList(matchEntityList);
    }

    @Override
    public MatchDto getMatch(Long id) throws MatchNotFoundException {
        Optional<MatchEntity> matchEntityOptional = this.matchRepository.findById(id);

        if (matchEntityOptional.isEmpty()) {
            throw new MatchNotFoundException(String.valueOf(id));
        }

        return this.matchMapper.toMatchDto(matchEntityOptional.get());
    }

    @Override
    public MatchDto createMatch(MatchDto match) {
        MatchEntity matchEntity = this.matchMapper.toMatchEntity(match);

        MatchEntity savedMatchEntity = this.matchRepository.save(matchEntity);

        return this.matchMapper.toMatchDto(savedMatchEntity);
    }

    @Override
    public MatchDto updateMatch(MatchDto match) {
        MatchEntity matchEntity = this.matchMapper.toMatchEntity(match);

        MatchEntity updatedMatchEntity = this.matchRepository.save(matchEntity);

        return this.matchMapper.toMatchDto(updatedMatchEntity);
    }

    @Override
    @Transactional
    public void deleteMatch(Long id) {
        this.matchRepository.deleteById(id);
    }

    @Override
    public MatchDto addPlayerToMatch(Long matchId, String playerUsername) throws UserNotFoundException,
            MatchNotFoundException, PlayerAlreadyInMatchException {
        Optional<UserEntity> userEntityOptional = this.userRepository.findByUsername(playerUsername);
        Optional<MatchEntity> matchEntityOptional = this.matchRepository.findById(matchId);

        if (userEntityOptional.isEmpty()) throw new UserNotFoundException(playerUsername);
        if (matchEntityOptional.isEmpty()) throw new MatchNotFoundException(String.valueOf(matchId));

        final MatchPlayerEntity matchPlayerEntity = new MatchPlayerEntity();
        matchPlayerEntity.setUser(userEntityOptional.get());
        matchPlayerEntity.setMatch(matchEntityOptional.get());
        matchPlayerEntity.setSubscriptionDate(new Date());

        try {
            MatchPlayerEntity savedMatchPlayerEntity = this.matchPlayerRepository.save(matchPlayerEntity);

            return this.matchMapper.toMatchDto(savedMatchPlayerEntity.getMatch());
        } catch (DataIntegrityViolationException e) {
            throw new PlayerAlreadyInMatchException(playerUsername);
        }
    }

    @Override
    @Transactional
    public void removePlayerFromMatch(Long matchId, String playerUsername) throws MatchNotFoundException, UserNotInMatchException {
        this.matchRepository.findById(matchId)
                .orElseThrow(() -> new MatchNotFoundException(String.valueOf(matchId)));

        MatchPlayerEntity matchPlayer = this.matchPlayerRepository
                .findByUser_UsernameAndMatch_Id(playerUsername, matchId)
                .orElseThrow(() -> new UserNotInMatchException(playerUsername));

        this.matchPlayerRepository.delete(matchPlayer);
        this.matchPlayerRepository.flush();
    }
}
