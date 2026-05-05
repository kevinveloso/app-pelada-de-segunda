package com.peladadesegunda.app.model;

import com.peladadesegunda.app.enumeration.Position;
import com.peladadesegunda.app.enumeration.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "app_user")
@Getter
@Setter
public class UserEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, length = 40)
    private String username;

    @Column(name = "password", length = 250)
    private String password;

    @Column(name = "name", nullable = false, length = 250)
    private String name;

    @Column(name = "nickname", length = 50)
    private String nickname;

    @Column(name = "birthdate")
    private Date birthdate;

    @Column(name = "is_regular_member", nullable = false)
    private Boolean regularMember;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false, length = 10)
    private UserRole role;

    @Column(name = "grade_average", precision = 3, scale = 2)
    private BigDecimal gradeAverage;

    @Column(name = "last_grade", precision = 3, scale = 2)
    private BigDecimal lastMatchGrade;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "player_position",
            joinColumns = @JoinColumn(name = "id_user")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "position_name", nullable = false, length = 50)
    private Set<Position> positionSet = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<MatchPlayerEntity> matchPlayerSet = new HashSet<>();
}
