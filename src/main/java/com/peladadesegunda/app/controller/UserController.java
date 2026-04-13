package com.peladadesegunda.app.controller;

import com.peladadesegunda.app.dto.PerformanceEvaluationDto;
import com.peladadesegunda.app.dto.UserDto;
import com.peladadesegunda.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@RequestParam Long id) {
        return ResponseEntity.ok(this.userService.getUser(id));
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto user) {
        UserDto createdUser = this.userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@RequestParam Long id, @RequestBody UserDto user) {
        return ResponseEntity.ok(this.userService.updateUser(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@RequestParam Long id) {
        this.userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/evaluation")
    public ResponseEntity<Void> evaluatePerformances(@RequestBody PerformanceEvaluationDto performanceEvaluationDto) {
        this.userService.evaluatePerformances(performanceEvaluationDto);
        return ResponseEntity.noContent().build();
    }
}
