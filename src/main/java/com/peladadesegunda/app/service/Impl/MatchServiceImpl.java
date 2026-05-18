package com.peladadesegunda.app.service.Impl;

import com.peladadesegunda.app.dto.*;
import com.peladadesegunda.app.exception.*;
import com.peladadesegunda.app.mapper.AbstractMatchMapper;
import com.peladadesegunda.app.mapper.AbstractPlayerMapper;
import com.peladadesegunda.app.model.MatchEntity;
import com.peladadesegunda.app.model.MatchPlayerEntity;
import com.peladadesegunda.app.model.PlayerEntity;
import com.peladadesegunda.app.repository.MatchPlayerRepository;
import com.peladadesegunda.app.repository.MatchRepository;
import com.peladadesegunda.app.repository.PlayerRepository;
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
    private AbstractPlayerMapper playerMapper;

    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private MatchPlayerRepository matchPlayerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PlayerRepository playerRepository;

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
    public MatchDto createMatch(AddUpdateMatchDto match) throws MatchCreationNotAllowed {
        Objects.requireNonNull(match.getMatchStartDate(), "Match start date can't be null.");
        Objects.requireNonNull(match.getMatchEndDate(), "Match end date can't be null.");

        if (!isMatchCreationAllowed(match)) throw new MatchCreationNotAllowed();

        MatchEntity matchEntity = this.matchMapper.toMatchEntity(match);

        List<PlayerEntity> regularMembersList = this.playerRepository.findByRegularMemberTrueOrderByNameAsc();

        Set<MatchPlayerEntity> matchPlayerEntitySet = new HashSet<>();

        regularMembersList.forEach(rm -> {
            MatchPlayerEntity matchPlayerEntity = new MatchPlayerEntity();

            matchPlayerEntity.setPlayer(rm);
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

        MatchEntity matchEntity = getMatchIfExists(match.getId());

        if (Objects.nonNull(match.getMatchStartDate())) matchEntity.setMatchStartDate(match.getMatchStartDate());

        if (Objects.nonNull(match.getMatchEndDate())) matchEntity.setMatchEndDate(match.getMatchEndDate());

        if (Objects.nonNull(match.getMaxPlayers())) matchEntity.setMaxPlayers(match.getMaxPlayers());

        MatchEntity updatedMatchEntity = this.matchRepository.save(matchEntity);

        return this.matchMapper.toMatchDto(updatedMatchEntity);
    }

    @Override
    @Transactional
    public void deleteMatch(Long id) {
        this.matchRepository.deleteById(id);
    }

    @Override
    public MatchDto addPlayerToMatch(Long matchId, Long playerId) throws PlayerNotFoundException,
            MatchNotFoundException, PlayerAlreadyInMatchException {
        Objects.requireNonNull(playerId, "Player ID can't be null!");
        Objects.requireNonNull(matchId, "Match ID can't be null!");

        Optional<PlayerEntity> playerEntityOptional = this.playerRepository.findById(playerId);
        Optional<MatchEntity> matchEntityOptional = this.matchRepository.findById(matchId);

        if (playerEntityOptional.isEmpty()) throw new PlayerNotFoundException(String.valueOf(playerId));
        if (matchEntityOptional.isEmpty()) throw new MatchNotFoundException(String.valueOf(matchId));

        final MatchPlayerEntity matchPlayerEntity = new MatchPlayerEntity();
        matchPlayerEntity.setPlayer(playerEntityOptional.get());
        matchPlayerEntity.setMatch(matchEntityOptional.get());
        matchPlayerEntity.setSubscriptionDate(new Date());

        try {
            MatchPlayerEntity savedMatchPlayerEntity = this.matchPlayerRepository.save(matchPlayerEntity);

            return this.matchMapper.toMatchDto(savedMatchPlayerEntity.getMatch());
        } catch (DataIntegrityViolationException e) {
            throw new PlayerAlreadyInMatchException(String.valueOf(playerId));
        }
    }

    @Override
    public List<MatchFromPlayerDto> getMatchesFromPlayer(Long playerId, Pageable pageable) throws PlayerNotFoundException {
        Optional<PlayerEntity> playerEntityOptional = this.playerRepository.findById(playerId);

        if (playerEntityOptional.isEmpty()) {
            throw new PlayerNotFoundException(String.valueOf(playerId));
        }

        Page<MatchPlayerEntity> page = this.matchPlayerRepository
                .findAllByPlayer_IdOrderByMatch_MatchStartDateAsc(playerEntityOptional.get().getId(), pageable);

        if (page.isEmpty()) return new ArrayList<>();

        return this.matchMapper.toMatchFromPlayerDtoList(page.stream().toList());
    }

    @Override
    @Transactional
    public void removePlayerFromMatch(Long matchId, Long playerId) throws MatchNotFoundException,
            PlayerNotInMatchException, PlayerNotFoundException {
        Objects.requireNonNull(playerId, "Player ID can't be null!");
        Objects.requireNonNull(matchId, "Match ID can't be null!");

        this.matchRepository.findById(matchId)
                .orElseThrow(() -> new MatchNotFoundException(String.valueOf(matchId)));

        PlayerEntity playerEntity = this.playerRepository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException(String.valueOf(playerId)));

        MatchPlayerEntity matchPlayer = this.matchPlayerRepository
                .findByPlayer_IdAndMatch_Id(playerEntity.getId(), matchId)
                .orElseThrow(() -> new PlayerNotInMatchException(String.valueOf(playerId)));

        this.matchPlayerRepository.delete(matchPlayer);
        this.matchPlayerRepository.flush();
    }

    @Override
    public TeamsDto drawTeams(DrawTeamsDto drawTeamsDto) throws MatchNotFoundException, MatchIsOverException {
        Objects.requireNonNull(drawTeamsDto.getMatchId(), "Match ID can't be null!");

        MatchEntity matchEntity = getMatchIfExists(drawTeamsDto.getMatchId());

        Date now = new Date();
        if (now.after(matchEntity.getMatchEndDate())) {
            throw new MatchIsOverException(String.valueOf(drawTeamsDto.getMatchId()));
        }

        TeamsDto resultTeamsDto = new TeamsDto();
        resultTeamsDto.setMatchId(drawTeamsDto.getMatchId());

        List<MatchPlayerEntity> matchPlayerEntityList = null;

        switch (drawTeamsDto.getDrawStyle()) {
            case BALANCED -> { matchPlayerEntityList = this.balancedDraw(matchEntity); }
            case POSITION_BALANCED -> { matchPlayerEntityList = this.positionBalancedDraw(matchEntity); }
            default -> { matchPlayerEntityList = this.blindDraw(matchEntity); }
        }

        matchPlayerEntityList.forEach(m -> {
            if (m.getTeam() == 0) {
                resultTeamsDto.getTeamA().add(this.playerMapper.toPlayerDto(m.getPlayer()));
            } else if (m.getTeam() == 1) {
                resultTeamsDto.getTeamB().add(this.playerMapper.toPlayerDto(m.getPlayer()));
            }
        });

        return resultTeamsDto;
    }

    @Override
    public MatchResultDto getMatchResult(Long matchId) throws MatchNotFoundException {
        Objects.requireNonNull(matchId, "Match ID can't be null!");

        getMatchIfExists(matchId);



        return null;
    }

    @Override
    public MatchDto getCurrentMatch() throws MatchNotFoundException {
        Date now = new Date();
        Optional<MatchEntity> matchEntityOptional = this.matchRepository.findByMatchStartDateLessThanEqualAndMatchEndDateGreaterThan(now, now);

        if (matchEntityOptional.isEmpty()) {
            throw new MatchNotFoundException("current");
        }

        return this.matchMapper.toMatchDto(matchEntityOptional.get());
    }

    private MatchEntity getMatchIfExists(Long drawTeamsDto) throws MatchNotFoundException {
        Optional<MatchEntity> matchEntityOptional = this.matchRepository.findById(drawTeamsDto);

        if (matchEntityOptional.isEmpty()) throw new MatchNotFoundException(String.valueOf(drawTeamsDto));

        return matchEntityOptional.get();
    }

    private List<MatchPlayerEntity> balancedDraw(MatchEntity match) {
        return new ArrayList<>();
    }

    private List<MatchPlayerEntity> positionBalancedDraw(MatchEntity match) {
        return new ArrayList<>();
    }

    private List<MatchPlayerEntity> blindDraw(MatchEntity match) {
        List<MatchPlayerEntity> listOfSubscribedPlayers = getListOfSubscribedPlayers(match);

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

    private static List<MatchPlayerEntity> getListOfSubscribedPlayers(MatchEntity match) {
        List<MatchPlayerEntity> listOfSubscribedPlayers = new ArrayList<>(match.getMatchPlayerSet());

        listOfSubscribedPlayers.sort(Comparator.comparing(MatchPlayerEntity::getSubscriptionDate));

        if (listOfSubscribedPlayers.size() > match.getMaxPlayers()) {
            listOfSubscribedPlayers = listOfSubscribedPlayers.subList(0, match.getMaxPlayers());
        }

        Collections.shuffle(listOfSubscribedPlayers);
        return listOfSubscribedPlayers;
    }

    private Boolean isMatchCreationAllowed(AddUpdateMatchDto match) {
        Date now = new Date();

        if (match.getMatchStartDate().after(now) && match.getMatchEndDate().after(match.getMatchStartDate())) {
            List<MatchEntity> matchEntityList = this.matchRepository
                    .findAllByMatchStartDateGreaterThanEqualAndMatchStartDateLessThan(match.getMatchStartDate(), match.getMatchEndDate());

            return matchEntityList.isEmpty();
        }

        return false;
    }
}
