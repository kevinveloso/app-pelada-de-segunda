package com.peladadesegunda.app.repository;

import com.peladadesegunda.app.model.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {
    List<PlayerEntity> findByRegularMemberTrueOrderByNameAsc();
}
