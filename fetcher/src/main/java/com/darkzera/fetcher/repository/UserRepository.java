package com.darkzera.fetcher.repository;

import com.darkzera.fetcher.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT name FROM sqlite_master WHERE type='table'", nativeQuery = true)
    List<?> show();
}
