package com.yaren.eventplanner.repository;

import com.yaren.eventplanner.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);

}