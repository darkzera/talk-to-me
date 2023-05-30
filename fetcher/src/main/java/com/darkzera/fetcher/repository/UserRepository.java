package com.darkzera.fetcher.repository;

import com.darkzera.fetcher.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserProfile, Long> {

    @Query(value = "SELECT name FROM sqlite_master WHERE type='table'", nativeQuery = true)
    List<?> show();

    boolean existsByEmail(String email);

    Optional<UserProfile> findUserProfileByEmail(String email);
    @Query(value = "SELECT users.email FROM users JOIN users.availableIn d WHERE users.availableIn IS NOT NULL", nativeQuery = true)
    Object findDatesAfter(@Param("date") LocalDateTime date);
    @Query(value = "SELECT u.email FROM users u WHERE u.email = :email", nativeQuery = true)
    Object disponivel(@Param("email") String email);

    @Query(value = "SELECT * FROM "
            +"(SELECT available_in FROM users join USER_PROFILE_AVAILABLE_IN)" +
            "WHERE AVAILABLE_IN between '2020-05-28 08:00:00' and :limit",
            nativeQuery = true)
    Object findAvailableDatesUtilGivenDate(@Param("limit") LocalDateTime limit);
}
