package com.peladadesegunda.app.controller;

import com.peladadesegunda.app.dto.MatchFromUserDto;
import com.peladadesegunda.app.dto.PerformanceEvaluationDto;
import com.peladadesegunda.app.dto.UserDto;
import com.peladadesegunda.app.exception.UsernameAlreadyExistsException;
import com.peladadesegunda.app.exception.UserNotFoundException;
import com.peladadesegunda.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/player")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(Pageable pageable) {
        return ResponseEntity.ok(this.userService.getAllUsers(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(this.userService.getUser(id));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto user) {
        try {
            UserDto createdUser = this.userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (UsernameAlreadyExistsException e) {
           return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto user) {
        ResponseEntity<UserDto> response = null;
        try {
            response = ResponseEntity.ok(this.userService.updateUser(user));
        } catch (UserNotFoundException e) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (UsernameAlreadyExistsException e) {
            response = ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        this.userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/evaluation")
    public ResponseEntity<Void> evaluatePerformances(@PathVariable PerformanceEvaluationDto performanceEvaluationDto) {
        this.userService.evaluatePerformances(performanceEvaluationDto);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{username}")
    public ResponseEntity<List<MatchFromUserDto>> getMatchesFromUser(@PathVariable String username, Pageable pageable) {
        try {
            return ResponseEntity.ok(this.userService.getMatchesFromUser(username, pageable));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
