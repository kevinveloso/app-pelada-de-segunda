package com.peladadesegunda.app.repository;

import com.peladadesegunda.app.model.MatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MatchRepository extends JpaRepository<MatchEntity, Long> {
    List<MatchEntity> findAllByMatchStartDateGreaterThanEqualAndMatchStartDateLessThan(Date start, Date end);
    Optional<MatchEntity> findByMatchStartDateLessThanEqualAndMatchEndDateGreaterThan(Date now, Date now2);
}
