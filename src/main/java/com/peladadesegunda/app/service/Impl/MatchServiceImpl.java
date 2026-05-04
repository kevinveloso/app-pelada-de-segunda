package com.peladadesegunda.app.service.Impl;

import com.peladadesegunda.app.dto.AddUpdateMatchDto;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    public List<MatchDto> getAllMatches(Pageable pageable) {
        Page<MatchEntity> matchEntityList = this.matchRepository.findAll(pageable);

        if (matchEntityList.isEmpty()) {
            return new ArrayList<>();
        }

        return this.matchMapper.toMatchDtoList(matchEntityList.stream().toList());
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
    public MatchDto createMatch(AddUpdateMatchDto match) {
        MatchEntity matchEntity = this.matchMapper.toMatchEntity(match);

        List<UserEntity> regularMembersList = this.userRepository.findByRegularMemberTrueOrderByNameAsc();

        Set<MatchPlayerEntity> matchPlayerEntitySet = new HashSet<>();

        regularMembersList.forEach(rm -> {
            MatchPlayerEntity matchPlayerEntity = new MatchPlayerEntity();

            matchPlayerEntity.setUser(rm);
            matchPlayerEntity.setMatch(matchEntity);
            matchPlayerEntity.setSubscriptionDate(new Date());

            matchPlayerEntitySet.add(matchPlayerEntity);
        });

        matchEntity.setMatchPlayerSet(matchPlayerEntitySet);

        MatchEntity savedMatchEntity = this.matchRepository.save(matchEntity);

        return this.matchMapper.toMatchDto(savedMatchEntity);
    }

    @Override
    public MatchDto updateMatch(AddUpdateMatchDto match) throws MatchNotFoundException {
        Objects.requireNonNull(match.getId(), "ID can't be null!");

        Optional<MatchEntity> matchEntityOptional = this.matchRepository.findById(match.getId());

        if (matchEntityOptional.isEmpty()) throw new MatchNotFoundException(String.valueOf(match.getId()));

        if (Objects.nonNull(match.getMatchStartDate())) matchEntityOptional.get().setMatchStartDate(match.getMatchStartDate());

        if (Objects.nonNull(match.getMatchEndDate())) matchEntityOptional.get().setMatchStartDate(match.getMatchEndDate());

        if (Objects.nonNull(match.getMaxPlayers())) matchEntityOptional.get().setMaxPlayers(match.getMaxPlayers());

        MatchEntity updatedMatchEntity = this.matchRepository.save(matchEntityOptional.get());

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
        Objects.requireNonNull(playerUsername, "Player username can't be null!");
        Objects.requireNonNull(matchId, "Match ID can't be null!");

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
        Objects.requireNonNull(playerUsername, "Player username can't be null!");
        Objects.requireNonNull(matchId, "Match ID can't be null!");

        this.matchRepository.findById(matchId)
                .orElseThrow(() -> new MatchNotFoundException(String.valueOf(matchId)));

        MatchPlayerEntity matchPlayer = this.matchPlayerRepository
                .findByUser_UsernameAndMatch_Id(playerUsername, matchId)
                .orElseThrow(() -> new UserNotInMatchException(playerUsername));

        this.matchPlayerRepository.delete(matchPlayer);
        this.matchPlayerRepository.flush();
    }
}
