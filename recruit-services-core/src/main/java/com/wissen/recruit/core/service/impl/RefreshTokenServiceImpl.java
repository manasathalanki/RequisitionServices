package com.wissen.recruit.core.service.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wissen.recruit.core.entity.RefreshToken;
import com.wissen.recruit.core.entity.User;
import com.wissen.recruit.core.exception.RefreshTokenNotValidException;
import com.wissen.recruit.core.jwt.JwtHelper;
import com.wissen.recruit.core.repository.RefreshTokenRepository;
import com.wissen.recruit.core.service.RefreshTokenService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class RefreshTokenServiceImpl implements RefreshTokenService {

	@Autowired
	RefreshTokenRepository refreshTokenRepository;

	@Autowired
	JwtHelper jwtHelper;

	@Override
	public RefreshToken findByTokenValue(String token) throws RefreshTokenNotValidException {
		Optional<RefreshToken> refreshTokenEntity = refreshTokenRepository.findByTokenValue(token);
		
		if (!jwtHelper.validateRefreshToken(token) || !refreshTokenEntity.isPresent()) {
			log.error("Provided Refresh Token is Expired or Not Valid or Not Found in DB..");
			throw new RefreshTokenNotValidException("Refresh Token Expried or Not Valid...");
		}
		
		return refreshTokenEntity.get();
	}

	@Override
	public void deleteByUsername(String username) {
		refreshTokenRepository.deleteByUsername(username);
	}

	@Override
	public void delete(RefreshToken entity) {
		refreshTokenRepository.delete(entity);
	}

	@Override
	public RefreshToken save(User user, String token) {
		RefreshToken refreshToken = new RefreshToken(0, user.getId(), user.getUsername(), token, new Date(), true);
		return refreshTokenRepository.save(refreshToken);
	}

	@Override
	public void modify(RefreshToken newRefreshToken) {
		refreshTokenRepository.save(newRefreshToken);
	}

}
