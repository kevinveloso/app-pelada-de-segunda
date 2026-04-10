package com.peladadesegunda.app.service.Impl;

import com.peladadesegunda.app.dto.PerformanceEvaluationDto;
import com.peladadesegunda.app.dto.UserDto;
import com.peladadesegunda.app.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public List<UserDto> getAllUsers() {
        return new ArrayList<>();
    }

    @Override
    public UserDto getUser(Long id) {
        return null;
    }

    @Override
    public UserDto createUser(UserDto user) {
        return null;
    }

    @Override
    public UserDto updateUser(Long id, UserDto user) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }

    @Override
    public void evaluatePerformances(PerformanceEvaluationDto performanceEvaluationDto) {

    }
}
