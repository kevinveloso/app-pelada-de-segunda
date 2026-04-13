package com.peladadesegunda.app.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "football_match")
@Data
public class MatchEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "match_date", nullable = false)
    private Date matchDate;

    @Column(name = "max_players", nullable = false)
    private Integer maxPlayers;

    @ManyToMany
    @JoinTable(
            name = "match_player",
            joinColumns = @JoinColumn(name = "id_match"),
            inverseJoinColumns = @JoinColumn(name = "id_user")
    )
    private Set<UserEntity> playerSet = new HashSet<>();
}
