package com.wissen.recruit.core.controller;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wissen.recruit.core.dto.LoginDTO;
import com.wissen.recruit.core.dto.SignUpDTO;
import com.wissen.recruit.core.dto.StatusDTO;
import com.wissen.recruit.core.dto.TokenDTO;
import com.wissen.recruit.core.entity.RefreshToken;
import com.wissen.recruit.core.entity.User;
import com.wissen.recruit.core.exception.RefreshTokenNotValidException;
import com.wissen.recruit.core.exception.ValueAlreadyExistsException;
import com.wissen.recruit.core.jwt.JwtHelper;
import com.wissen.recruit.core.service.RefreshTokenService;
import com.wissen.recruit.core.service.UserService;

import lombok.extern.log4j.Log4j2;

@ConfigurationProperties
@Log4j2
@RestController
@RequestMapping("/auth")
public class UserAccessManagmentController {

	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	JwtHelper jwtHelper;
	@Autowired
	UserService userService;
	@Autowired
	RefreshTokenService refreshTokenService;

	@PostMapping("/signup")
	public ResponseEntity<StatusDTO> signUp(@Valid @RequestBody SignUpDTO dto) throws ValueAlreadyExistsException {
		return userService.saveUser(dto);
	}

	@PostMapping("/login")
	public ResponseEntity<TokenDTO> login(@Valid @RequestBody LoginDTO dto) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		User user = (User) authentication.getPrincipal();

		String refreshTokenStr = jwtHelper.generateRefreshToken(user);

		log.info("Login - Saving Refresh Token in DB...");
		RefreshToken refreshTokenEntity = refreshTokenService.save(user, refreshTokenStr);
		log.info("Login - Refresh Token Saved in DB.");

		String accessToken = jwtHelper.generateAccessToken(user, refreshTokenEntity.getId());
		log.info("Login - Login Successful for " + dto.getUsername() + ". Access Token and Refresh Token Generated.");
		return new ResponseEntity<>(new TokenDTO(new Date().toString(), HttpServletResponse.SC_OK, "Login Success",
				accessToken, refreshTokenStr), HttpStatus.OK);

	}

	@PostMapping("/accesstoken")
	public ResponseEntity<TokenDTO> generateNewAccessToken(@Valid @RequestBody TokenDTO dto)
			throws RefreshTokenNotValidException, UsernameNotFoundException {
		String refreshToken = dto.getRefreshToken();
		RefreshToken refreshTokenEntity = refreshTokenService.findByTokenValue(refreshToken);

		String username = jwtHelper.extractUsernameFromRefreshToken(refreshToken);
		User user = userService.loadUserByUsername(username);

		String accessToken = jwtHelper.generateAccessToken(user, refreshTokenEntity.getId());
		log.info("GenerateNewAccessToken - New Access Token Generated using Refresh Token Provided...");
		return ResponseEntity.ok(new TokenDTO(new Date().toString(), HttpServletResponse.SC_OK,
				"New Access Token Generated", accessToken, refreshToken));
	}

	@PostMapping("/refreshtoken")
	public ResponseEntity<TokenDTO> generateNewRefreshToken(@Valid @RequestBody TokenDTO dto)
			throws RefreshTokenNotValidException, UsernameNotFoundException {
		String refreshToken = dto.getRefreshToken();
		RefreshToken refreshTokenEntity = refreshTokenService.findByTokenValue(refreshToken);

		String username = jwtHelper.extractUsernameFromRefreshToken(refreshToken);
		User user = userService.loadUserByUsername(username);
		String accessToken = jwtHelper.generateAccessToken(user, refreshTokenEntity.getId());
		String newRefreshTokenStr = jwtHelper.generateRefreshToken(user);
		RefreshToken newRefreshToken = refreshTokenEntity;
		newRefreshToken.setTokenValue(newRefreshTokenStr);
		newRefreshToken.setCreatedDate(new Date());
		log.info("GenerateNewRefreshToken - Saving New Refresh Token in DB...");
		refreshTokenService.modify(newRefreshToken);
		log.info("GenerateNewRefreshToken - Saved New Refresh Token in DB.");

		log.info(
				"GenerateNewRefreshToken - New Refresh Token and Access Token Generated using Refresh Token Provided...");
		return ResponseEntity.ok(new TokenDTO(new Date().toString(), HttpServletResponse.SC_OK,
				"New Refresh Token Generated", accessToken, newRefreshTokenStr));
	}

	@PostMapping("/logout")
	public ResponseEntity<StatusDTO> logout(@Valid @RequestBody TokenDTO dto) throws RefreshTokenNotValidException {
		String refreshToken = dto.getRefreshToken();
		RefreshToken refreshTokenEntity = refreshTokenService.findByTokenValue(refreshToken);

		log.info("Logout - Removing Provided Refresh Token From DB...");
		refreshTokenService.delete(refreshTokenEntity);
		log.info("Logout - Removed Provided Refresh Token From DB...");

		log.info("Logout - Current Session Logout Successful.");
		return ResponseEntity
				.ok(new StatusDTO(new Date().toString(), HttpServletResponse.SC_OK, null, "Logout Successful..."));
	}

	@PostMapping("/logout-all")
	public ResponseEntity<StatusDTO> logoutAll(@Valid @RequestBody TokenDTO dto) throws RefreshTokenNotValidException {
		String refreshToken = dto.getRefreshToken();
		String username = jwtHelper.extractUsernameFromRefreshToken(refreshToken);
		refreshTokenService.findByTokenValue(refreshToken);
		log.info("Logout All - Removing Refresh Tokens Generated for " + username + " From DB...");
		refreshTokenService.deleteByUsername(username);
		log.info("Logout All - Removed Refresh Tokens Generated for " + username + " From DB...");

		log.info("Logout - All Sessions Logout Successful.");
		return ResponseEntity.ok(new StatusDTO(new Date().toString(), HttpServletResponse.SC_OK, null,
				"All Sessions Logout Successful..."));
	}

}
