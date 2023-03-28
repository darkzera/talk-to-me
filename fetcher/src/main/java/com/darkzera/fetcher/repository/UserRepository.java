package com.darkzera.fetcher.repository;

import com.darkzera.fetcher.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserProfile, Long> {

    @Query(value = "SELECT name FROM sqlite_master WHERE type='table'", nativeQuery = true)
    List<?> show();

    boolean existsByEmail(String email);

    Optional<UserProfile> findUserProfileByEmail(String email);
}
