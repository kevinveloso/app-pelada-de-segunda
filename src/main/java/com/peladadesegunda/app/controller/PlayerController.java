package com.peladadesegunda.app.controller;

import com.peladadesegunda.app.dto.PerformanceEvaluationDto;
import com.peladadesegunda.app.dto.PlayerDto;
import com.peladadesegunda.app.exception.PlayerNotFoundException;
import com.peladadesegunda.app.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/player")
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @GetMapping
    public ResponseEntity<List<PlayerDto>> getAllPlayers(Pageable pageable) {
        return ResponseEntity.ok(this.playerService.getAllPlayers(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerDto> getPlayer(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(this.playerService.getPlayer(id));
        } catch (PlayerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<PlayerDto> createPlayer(@RequestBody PlayerDto player) {

        PlayerDto createdPlayer = this.playerService.createPlayer(player);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPlayer);
    }

    @PutMapping
    public ResponseEntity<PlayerDto> updatePlayer(@RequestBody PlayerDto player) {
        ResponseEntity<PlayerDto> response = null;
        try {
            response = ResponseEntity.ok(this.playerService.updatePlayer(player));
        } catch (PlayerNotFoundException e) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        this.playerService.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/evaluation")
    public ResponseEntity<Void> evaluatePerformances(@PathVariable PerformanceEvaluationDto performanceEvaluationDto) {
        this.playerService.evaluatePerformances(performanceEvaluationDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/regular-members")
    public ResponseEntity<List<PlayerDto>> getAllRegularMembers() {
        return ResponseEntity.ok(this.playerService.getAllRegularMembers());
    }

    @GetMapping("/available-players/{matchId}")
    public ResponseEntity<List<PlayerDto>> getAllAvailablePlayers(@PathVariable Long matchId, Pageable pageable) {
        return ResponseEntity.ok(this.playerService.getAllAvailablePlayerFromMatch(matchId, pageable));
    }
}
