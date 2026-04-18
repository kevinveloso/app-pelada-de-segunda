package com.peladadesegunda.app.repository;

import com.peladadesegunda.app.model.MatchPlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchPlayerRepository  extends JpaRepository<MatchPlayerEntity, Long> {
}
