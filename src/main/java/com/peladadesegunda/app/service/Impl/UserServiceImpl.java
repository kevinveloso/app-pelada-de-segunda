package com.peladadesegunda.app.service.Impl;

import com.peladadesegunda.app.dto.PerformanceEvaluationDto;
import com.peladadesegunda.app.dto.UserDto;
import com.peladadesegunda.app.exception.UserNotFoundException;
import com.peladadesegunda.app.mapper.AbstractUserMapper;
import com.peladadesegunda.app.model.UserEntity;
import com.peladadesegunda.app.repository.UserRepository;
import com.peladadesegunda.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AbstractUserMapper userMapper;

    @Override
    public List<UserDto> getAllUsers() {
        List<UserEntity> userEntityList = this.userRepository.findAll();

        if (userEntityList.isEmpty()) {
            return new ArrayList<>();
        }

        return this.userMapper.toUserDtoList(userEntityList);
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
    public UserDto createUser(UserDto user) {
        UserEntity userEntity = this.userMapper.toUserEntity(user);

        UserEntity savedUserEntity = this.userRepository.save(userEntity);

        return this.userMapper.toUserDto(savedUserEntity);
    }

    @Override
    public UserDto updateUser(UserDto user) {
        UserEntity userEntity = this.userMapper.toUserEntity(user);

        UserEntity updatedUserEntity = this.userRepository.save(userEntity);

        return this.userMapper.toUserDto(updatedUserEntity);
    }

    @Override
    public void deleteUser(Long id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public void evaluatePerformances(PerformanceEvaluationDto performanceEvaluationDto) {

    }
}
