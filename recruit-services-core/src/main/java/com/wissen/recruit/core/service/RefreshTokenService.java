package com.wissen.recruit.core.service;

import com.wissen.recruit.core.entity.RefreshToken;
import com.wissen.recruit.core.entity.User;
import com.wissen.recruit.core.exception.RefreshTokenNotValidException;

public interface RefreshTokenService {

	RefreshToken findByTokenValue(String token) throws RefreshTokenNotValidException;

	void deleteByUsername(String username);

	void delete(RefreshToken entity);

	RefreshToken save(User user,String token);

	void modify(RefreshToken newRefreshToken);
	
}
