package com.peladadesegunda.app.repository;

import com.peladadesegunda.app.model.PlayerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {
    List<PlayerEntity> findByRegularMemberTrueOrderByNameAsc();

    @Query("""
                SELECT p
                FROM PlayerEntity p
                WHERE p.id NOT IN (
                    SELECT mp.player.id
                    FROM MatchPlayerEntity mp
                    WHERE mp.match.id = :matchId
                )
            """)
    Page<PlayerEntity> findAllAvailableFromMatch(@Param("matchId") Long matchId, Pageable pageable);
}
