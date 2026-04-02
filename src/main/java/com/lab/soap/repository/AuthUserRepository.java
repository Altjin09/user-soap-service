package com.lab.soap.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lab.soap.entity.AuthUser;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
    Optional<AuthUser> findByUsername(String username);
}