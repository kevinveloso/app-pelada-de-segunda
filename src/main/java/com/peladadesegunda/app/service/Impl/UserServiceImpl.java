package com.peladadesegunda.app.service.Impl;

import com.peladadesegunda.app.dto.UserDto;
import com.peladadesegunda.app.exception.UserNotFoundException;
import com.peladadesegunda.app.exception.UsernameAlreadyExistsException;
import com.peladadesegunda.app.mapper.AbstractPlayerMapper;
import com.peladadesegunda.app.mapper.AbstractUserMapper;
import com.peladadesegunda.app.model.PlayerEntity;
import com.peladadesegunda.app.model.UserEntity;
import com.peladadesegunda.app.repository.PlayerRepository;
import com.peladadesegunda.app.repository.UserRepository;
import com.peladadesegunda.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private AbstractUserMapper userMapper;
    @Autowired
    private AbstractPlayerMapper playerMapper;

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
    @Transactional
    public UserDto createUser(UserDto user) throws UsernameAlreadyExistsException {
        UserEntity userEntity = this.userMapper.toUserEntity(user);

        try {
            PlayerEntity savedPlayerEntity = playerRepository.save(userEntity.getPlayer());
            userEntity.setPlayer(savedPlayerEntity);

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
}
