package com.lab.soap.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lab.soap.entity.AuthUser;
import com.lab.soap.repository.AuthUserRepository;
import com.lab.soap.util.JwtUtil;

import io.jsonwebtoken.Claims;

@Service
public class AuthService {

    @Autowired
    private AuthUserRepository repository;

    @Autowired
    private JwtUtil jwtUtil;

    public String register(String username, String password) {
        Optional<AuthUser> existing = repository.findByUsername(username);
        if (existing.isPresent()) {
            return "Username already exists";
        }

        AuthUser user = new AuthUser(username, password);
        repository.save(user);
        return "Register success";
    }

    public String login(String username, String password) {
        Optional<AuthUser> userOpt = repository.findByUsername(username);
        if (userOpt.isEmpty()) {
            return null;
        }

        AuthUser user = userOpt.get();
        if (!user.getPassword().equals(password)) {
            return null;
        }

        return jwtUtil.generateToken(user.getId(), user.getUsername());
    }

    public boolean validate(String token) {
        try {
            jwtUtil.validateToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = jwtUtil.validateToken(token);
        return ((Integer) claims.get("userId")).longValue();
    }

    public String getUsernameFromToken(String token) {
        Claims claims = jwtUtil.validateToken(token);
        return claims.getSubject();
    }
}