package com.peladadesegunda.app.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "football_match")
@Getter
@Setter
public class MatchEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "match_start_date", nullable = false)
    private Date matchStartDate;

    @Column(name = "match_end_date", nullable = false)
    private Date matchEndDate;

    @Column(name = "max_players", nullable = false)
    private Integer maxPlayers;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MatchPlayerEntity> matchPlayerSet = new HashSet<>();
}
