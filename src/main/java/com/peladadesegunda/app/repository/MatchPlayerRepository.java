package com.peladadesegunda.app.repository;

import com.peladadesegunda.app.model.MatchPlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MatchPlayerRepository  extends JpaRepository<MatchPlayerEntity, Long> {

    Optional<MatchPlayerEntity> findByUser_UsernameAndMatch_Id(String userUsername, Long matchId);

}
