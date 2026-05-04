package com.peladadesegunda.app.repository;

import com.peladadesegunda.app.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository  extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);

    List<UserEntity>  findByRegularMemberTrueOrderByNameAsc();
}
