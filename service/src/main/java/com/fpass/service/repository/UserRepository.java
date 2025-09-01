package com.fpass.service.repository;

import com.fpass.service.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {

    @Query(value = "select * from users u where lower(u.username) = lower(:username)", nativeQuery = true)
    Optional<Users> findByUsernameNativeIgnoreCase(@Param("username") String username);
}