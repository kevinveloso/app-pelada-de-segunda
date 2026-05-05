package com.peladadesegunda.app.service.Impl;

import com.peladadesegunda.app.dto.*;
import com.peladadesegunda.app.exception.*;
import com.peladadesegunda.app.mapper.AbstractMatchMapper;
import com.peladadesegunda.app.mapper.AbstractUserMapper;
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
    private AbstractUserMapper userMapper;

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

        if (Objects.nonNull(match.getMatchEndDate())) matchEntityOptional.get().setMatchEndDate(match.getMatchEndDate());

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
    public List<MatchFromUserDto> getMatchesFromUser(String username, Pageable pageable) throws UserNotFoundException {
        Optional<UserEntity> userEntityOptional = this.userRepository.findByUsername(username);

        if (userEntityOptional.isEmpty()) {
            throw new UserNotFoundException(username);
        }

        Page<MatchPlayerEntity> page = this.matchPlayerRepository.findAllByUser_IdOrderByMatch_MatchStartDateAsc(userEntityOptional.get().getId(), pageable);

        if (page.isEmpty()) return new ArrayList<>();

        return this.matchMapper.toMatchFromUserDtoList(page.stream().toList());
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

    @Override
    public TeamsDto drawTeams(DrawTeamsDto drawTeamsDto) throws MatchNotFoundException, MatchIsOverException {
        Objects.requireNonNull(drawTeamsDto.getMatchId(), "Match ID can't be null!");

        Optional<MatchEntity> matchEntityOptional = this.matchRepository.findById(drawTeamsDto.getMatchId());

        if (matchEntityOptional.isEmpty()) throw new MatchNotFoundException(String.valueOf(drawTeamsDto.getMatchId()));

        Date now = new Date();
        if (now.after(matchEntityOptional.get().getMatchEndDate())) {
            throw new MatchIsOverException(String.valueOf(drawTeamsDto.getMatchId()));
        }

        TeamsDto resultTeamsDto = new TeamsDto();
        resultTeamsDto.setMatchId(drawTeamsDto.getMatchId());

        List<MatchPlayerEntity> matchPlayerEntityList = null;

        switch (drawTeamsDto.getDrawStyle()) {
            case BALANCED -> { matchPlayerEntityList = this.balancedDraw(matchEntityOptional.get()); }
            case POSITION_BALANCED -> { matchPlayerEntityList = this.positionBalancedDraw(matchEntityOptional.get()); }
            default -> { matchPlayerEntityList = this.blindDraw(matchEntityOptional.get()); }
        }

        matchPlayerEntityList.forEach(m -> {
            if (m.getTeam() == 0) {
                resultTeamsDto.getTeamA().add(this.userMapper.toUserDto(m.getUser()));
            } else if (m.getTeam() == 1) {
                resultTeamsDto.getTeamB().add(this.userMapper.toUserDto(m.getUser()));
            }
        });

        return resultTeamsDto;
    }

    private List<MatchPlayerEntity> balancedDraw(MatchEntity match) {
        return new ArrayList<>();
    }

    private List<MatchPlayerEntity> positionBalancedDraw(MatchEntity match) {
        return new ArrayList<>();
    }

    private List<MatchPlayerEntity> blindDraw(MatchEntity match) {
        List<MatchPlayerEntity> listOfSubscribedPlayers = new ArrayList<>(match.getMatchPlayerSet());

        listOfSubscribedPlayers.sort(Comparator.comparing(MatchPlayerEntity::getSubscriptionDate));

        if (listOfSubscribedPlayers.size() > match.getMaxPlayers()) {
            listOfSubscribedPlayers = listOfSubscribedPlayers.subList(match.getMaxPlayers(), listOfSubscribedPlayers.size());
        }

        Collections.shuffle(listOfSubscribedPlayers);

        for (int i = 0; i < listOfSubscribedPlayers.size(); i++) {

            if (i < (listOfSubscribedPlayers.size()/2)) {
                listOfSubscribedPlayers.get(i).setTeam(0);
            } else {
                listOfSubscribedPlayers.get(i).setTeam(1);
            }

            this.matchPlayerRepository.save(listOfSubscribedPlayers.get(i));
        }

        return listOfSubscribedPlayers;
    }
}
