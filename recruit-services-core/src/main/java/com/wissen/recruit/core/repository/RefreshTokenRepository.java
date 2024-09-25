package com.wissen.recruit.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wissen.recruit.core.entity.RefreshToken;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {

	void deleteByUsername(String username);

	void deleteByTokenValue(String refreshToken);

	Optional<RefreshToken> findByTokenValue(String refreshToken);
	
	Optional<RefreshToken> findById(Integer tokenId);

}
