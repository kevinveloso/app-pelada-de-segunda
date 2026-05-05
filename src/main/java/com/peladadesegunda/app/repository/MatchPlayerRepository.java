package com.peladadesegunda.app.repository;

import com.peladadesegunda.app.model.MatchPlayerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MatchPlayerRepository  extends JpaRepository<MatchPlayerEntity, Long> {

    Optional<MatchPlayerEntity> findByPlayer_IdAndMatch_Id(Long playerId, Long matchId);

    Page<MatchPlayerEntity> findAllByPlayer_IdOrderByMatch_MatchStartDateAsc(Long id, Pageable pageable);
}
