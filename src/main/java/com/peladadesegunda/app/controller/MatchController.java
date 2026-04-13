package com.peladadesegunda.app.controller;

import com.peladadesegunda.app.dto.MatchDto;
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
        return ResponseEntity.ok(this.matchService.getMatch(id));
    }

    @PostMapping
    public ResponseEntity<MatchDto> createMatch(@RequestBody MatchDto match) {
        MatchDto createdMatch = this.matchService.createMatch(match);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMatch);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MatchDto> updateMatch(@RequestParam Long id, @RequestBody MatchDto match) {
        return ResponseEntity.ok(this.matchService.updateMatch(id, match));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatch(@RequestParam Long id) {
        this.matchService.deleteMatch(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{matchId}/add-player/{playerUsername}")
    public ResponseEntity<MatchDto> addPlayerToMatch(@RequestParam Long matchId, @RequestParam String playerUsername) {
        return ResponseEntity.ok(this.matchService.addPlayerToMatch(matchId, playerUsername));
    }

    @GetMapping("/{matchId}/remove-player/{playerUsername}")
    public ResponseEntity<MatchDto> removePlayerFromMatch(@RequestParam Long matchId, @RequestParam String playerUsername) {
        return ResponseEntity.ok(this.matchService.removePlayerFromMatch(matchId, playerUsername));
    }
}
