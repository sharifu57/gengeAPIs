package com.genge.GengeAPIs.user.repository;

import com.genge.GengeAPIs.user.entity.RefreshToken;
import com.genge.GengeAPIs.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> deleteByUser(User user);
    Optional<RefreshToken> findByUser(User user);
}
