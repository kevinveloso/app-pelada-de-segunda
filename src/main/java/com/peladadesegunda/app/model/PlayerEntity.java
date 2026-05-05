package com.peladadesegunda.app.model;

import com.peladadesegunda.app.enumeration.Position;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "player")
@Getter
@Setter
public class PlayerEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 250)
    private String name;

    @Column(name = "nickname", length = 50)
    private String nickname;

    @Column(name = "birthdate")
    private Date birthdate;

    @Column(name = "is_regular_member", nullable = false)
    private Boolean regularMember;

    @Column(name = "grade_average", precision = 3, scale = 2)
    private BigDecimal gradeAverage;

    @Column(name = "last_grade", precision = 3, scale = 2)
    private BigDecimal lastMatchGrade;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "player_position",
            joinColumns = @JoinColumn(name = "id_player")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "position_name", nullable = false, length = 50)
    private Set<Position> positionSet = new HashSet<>();

    @OneToMany(mappedBy = "player")
    private Set<MatchPlayerEntity> matchPlayerSet = new HashSet<>();
}
