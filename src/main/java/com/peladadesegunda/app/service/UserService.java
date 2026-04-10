package com.peladadesegunda.app.service;

import com.peladadesegunda.app.dto.PerformanceEvaluationDto;
import com.peladadesegunda.app.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers();

    UserDto getUser(Long id);

    UserDto createUser(UserDto user);

    UserDto updateUser(Long id, UserDto user);

    void deleteUser(Long id);

    void evaluatePerformances(PerformanceEvaluationDto performanceEvaluationDto);
}
