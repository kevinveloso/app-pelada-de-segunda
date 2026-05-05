package com.peladadesegunda.app.controller;

import com.peladadesegunda.app.dto.*;
import com.peladadesegunda.app.exception.*;
import com.peladadesegunda.app.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<List<MatchDto>> getAllMatches(Pageable pageable) {
        return ResponseEntity.ok(this.matchService.getAllMatches(pageable));
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
    public ResponseEntity<MatchDto> createMatch(@RequestBody AddUpdateMatchDto match) {
        MatchDto createdMatch = this.matchService.createMatch(match);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMatch);
    }

    @PutMapping
    public ResponseEntity<MatchDto> updateMatch(@RequestBody AddUpdateMatchDto match) {
        try {
            return ResponseEntity.ok(this.matchService.updateMatch(match));
        } catch (MatchNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable Long id) {
        this.matchService.deleteMatch(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{matchId}/add-player/{playerId}")
    public ResponseEntity<MatchDto> addPlayerToMatch(@PathVariable Long matchId, @PathVariable Long playerId) {
        ResponseEntity<MatchDto> response = null;

        try {
            response = ResponseEntity.ok(this.matchService.addPlayerToMatch(matchId, playerId));
        } catch (MatchNotFoundException | PlayerNotFoundException e) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (PlayerAlreadyInMatchException e) {
            response = ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return response;
    }

    @GetMapping("/player/{username}")
    public ResponseEntity<List<MatchFromPlayerDto>> getMatchesFromUser(@PathVariable String username, Pageable pageable) {
        try {
            return ResponseEntity.ok(this.matchService.getMatchesFromUser(username, pageable));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{matchId}/remove-player/{playerId}")
    public ResponseEntity<MatchDto> removePlayerFromMatch(@PathVariable Long matchId, @PathVariable Long playerId) {
        try {
            this.matchService.removePlayerFromMatch(matchId, playerId);
            return ResponseEntity.noContent().build();
        } catch (MatchNotFoundException | PlayerNotFoundException | PlayerNotInMatchException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/draw")
    public ResponseEntity<TeamsDto> drawTeams(@RequestBody DrawTeamsDto drawTeamsDto) {
        ResponseEntity<TeamsDto> response = null;

        try {
            response = ResponseEntity.ok(this.matchService.drawTeams(drawTeamsDto));
        } catch (MatchNotFoundException e) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (MatchIsOverException e) {
            response = ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return response;
    }

    @GetMapping("/result/{id}")
    public ResponseEntity<MatchResultDto> getMatchResult(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(this.matchService.getMatchResult(id));
        } catch (MatchNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
