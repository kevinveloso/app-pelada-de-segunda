package com.peladadesegunda.app.service;

import com.peladadesegunda.app.dto.PerformanceEvaluationDto;
import com.peladadesegunda.app.dto.UserDto;
import com.peladadesegunda.app.exception.UsernameAlreadyExistsException;
import com.peladadesegunda.app.exception.UserNotFoundException;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers();

    UserDto getUser(Long id) throws UserNotFoundException;

    UserDto createUser(UserDto user) throws UsernameAlreadyExistsException;

    UserDto updateUser(UserDto user) throws UserNotFoundException, UsernameAlreadyExistsException;

    void deleteUser(Long id);

    void evaluatePerformances(PerformanceEvaluationDto performanceEvaluationDto);
}
