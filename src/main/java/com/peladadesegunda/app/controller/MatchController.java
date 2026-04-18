package com.peladadesegunda.app.controller;

import com.peladadesegunda.app.dto.MatchDto;
import com.peladadesegunda.app.exception.MatchNotFoundException;
import com.peladadesegunda.app.exception.UserNotFoundException;
import com.peladadesegunda.app.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/match")
public class MatchController {

    @Autowired
    public MatchService matchService;

    @GetMapping
    public ResponseEntity<List<MatchDto>> getAllMatches() {
        return ResponseEntity.ok(this.matchService.getAllMatches());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatchDto> getMatch(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(this.matchService.getMatch(id));
        } catch (MatchNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<MatchDto> createMatch(@RequestBody MatchDto match) {
        MatchDto createdMatch = this.matchService.createMatch(match);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMatch);
    }

    @PutMapping
    public ResponseEntity<MatchDto> updateMatch(@RequestBody MatchDto match) {
        return ResponseEntity.ok(this.matchService.updateMatch(match));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable Long id) {
        this.matchService.deleteMatch(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{matchId}/add-player/{playerUsername}")
    public ResponseEntity<MatchDto> addPlayerToMatch(@PathVariable Long matchId, @PathVariable String playerUsername) {
        try {
            return ResponseEntity.ok(this.matchService.addPlayerToMatch(matchId, playerUsername));
        } catch (MatchNotFoundException | UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{matchId}/remove-player/{playerUsername}")
    public ResponseEntity<MatchDto> removePlayerFromMatch(@PathVariable Long matchId, @PathVariable String playerUsername) {
        return ResponseEntity.ok(this.matchService.removePlayerFromMatch(matchId, playerUsername));
    }
}
