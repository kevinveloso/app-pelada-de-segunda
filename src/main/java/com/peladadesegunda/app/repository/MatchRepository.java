package com.peladadesegunda.app.repository;

import com.peladadesegunda.app.model.MatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<MatchEntity, Long> {
}
