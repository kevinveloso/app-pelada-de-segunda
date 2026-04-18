package com.peladadesegunda.app.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "match_player")
@Getter
@Setter
public class MatchPlayerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_user", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_match", nullable = false)
    private MatchEntity match;

    @Column(name = "team")
    private Integer team;

    @Column(name = "goals_scored")
    private Integer goalsScored;

    @Column(name = "subscription_date", nullable = false)
    private Date subscriptionDate;

    @Column(name = "was_present")
    private Boolean wasPresent;
}
