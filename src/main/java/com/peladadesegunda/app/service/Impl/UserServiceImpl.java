package com.peladadesegunda.app.service.Impl;

import com.peladadesegunda.app.dto.MatchFromUserDto;
import com.peladadesegunda.app.dto.PerformanceEvaluationDto;
import com.peladadesegunda.app.dto.UserDto;
import com.peladadesegunda.app.exception.UsernameAlreadyExistsException;
import com.peladadesegunda.app.exception.UserNotFoundException;
import com.peladadesegunda.app.mapper.AbstractMatchMapper;
import com.peladadesegunda.app.mapper.AbstractUserMapper;
import com.peladadesegunda.app.model.MatchPlayerEntity;
import com.peladadesegunda.app.model.UserEntity;
import com.peladadesegunda.app.repository.MatchPlayerRepository;
import com.peladadesegunda.app.repository.UserRepository;
import com.peladadesegunda.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MatchPlayerRepository matchPlayerRepository;

    @Autowired
    private AbstractUserMapper userMapper;
    @Autowired
    private AbstractMatchMapper matchMapper;

    @Override
    public List<UserDto> getAllUsers(Pageable pageable) {
        Page<UserEntity> userEntityList = this.userRepository.findAll(pageable);

        if (userEntityList.isEmpty()) {
            return new ArrayList<>();
        }

        return this.userMapper.toUserDtoList(userEntityList.stream().toList());
    }

    @Override
    public UserDto getUser(Long id) throws UserNotFoundException {
        Optional<UserEntity> userEntityOptional = this.userRepository.findById(id);

        if (userEntityOptional.isEmpty()) {
            throw new UserNotFoundException(String.valueOf(id));
        }

        return this.userMapper.toUserDto(userEntityOptional.get());
    }

    @Override
    public UserDto createUser(UserDto user) throws UsernameAlreadyExistsException {
        UserEntity userEntity = this.userMapper.toUserEntity(user);

        try {
            UserEntity savedUserEntity = this.userRepository.save(userEntity);

            return this.userMapper.toUserDto(savedUserEntity);
        } catch (DataIntegrityViolationException e) {
            throw new UsernameAlreadyExistsException(user.getUsername());
        }
    }

    @Override
    public UserDto updateUser(UserDto user) throws UserNotFoundException, UsernameAlreadyExistsException {
        Objects.requireNonNull(user.getId(), "ID can't be null!");

        Optional<UserEntity> userEntityOptional = this.userRepository.findById(user.getId());
        if (userEntityOptional.isEmpty()) throw new UserNotFoundException(String.valueOf(user.getId()));

        UserEntity userEntity = userEntityOptional.get();

        if (Objects.nonNull(user.getUsername())) userEntity.setUsername(user.getUsername());
        if (Objects.nonNull(user.getPassword())) userEntity.setPassword(user.getPassword());
        if (Objects.nonNull(user.getName())) userEntity.setName(user.getName());
        if (Objects.nonNull(user.getNickname())) userEntity.setNickname(user.getNickname());
        if (Objects.nonNull(user.getBirthdate())) userEntity.setBirthdate(user.getBirthdate());
        if (!user.getPositionList().isEmpty()) userEntity.setPositionSet(new HashSet<>(user.getPositionList()));
        if (Objects.nonNull(user.getRegularMember())) userEntity.setRegularMember(user.getRegularMember());

        try {
            UserEntity updatedUserEntity = this.userRepository.save(userEntity);

            return this.userMapper.toUserDto(updatedUserEntity);
        } catch (DataIntegrityViolationException e) {
            throw new UsernameAlreadyExistsException(user.getUsername());
        }
    }

    @Override
    public void deleteUser(Long id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public void evaluatePerformances(PerformanceEvaluationDto performanceEvaluationDto) {

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

    //todo get all mensalistas

}
