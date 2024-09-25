package com.wissen.recruit.core.controller;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wissen.recruit.core.dto.LoginDTO;
import com.wissen.recruit.core.dto.SignUpDTO;
import com.wissen.recruit.core.dto.StatusDTO;
import com.wissen.recruit.core.dto.TokenDTO;
import com.wissen.recruit.core.entity.RefreshToken;
import com.wissen.recruit.core.entity.User;
import com.wissen.recruit.core.exception.NotFoundException;
import com.wissen.recruit.core.exception.RefreshTokenNotValidException;
import com.wissen.recruit.core.exception.ValueAlreadyExistsException;
import com.wissen.recruit.core.jwt.JwtHelper;
import com.wissen.recruit.core.service.RefreshTokenService;
import com.wissen.recruit.core.service.UserService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserAccessManagmentControllerTest {

	@InjectMocks
	UserAccessManagmentController userAccessManagmentController;

	@Mock
	UserService userService;

	@Mock
	Authentication authentication;

	@Mock
	RefreshTokenService refreshTokenService;

	@Mock
	AuthenticationManager authenticationManager;

	@Mock
	JwtHelper jwtHelper;

	List<SignUpDTO> details = new ArrayList<>();

	SignUpDTO signUpDto;
	
	LoginDTO loginDto;
	
	User user;
	
	String refreshTokenStr;
	
	RefreshToken refreshTokenEntity;

	@BeforeEach
	public void setup() throws ValueAlreadyExistsException {
		MockitoAnnotations.openMocks(this);
		signUpDto = new SignUpDTO("kamesh", "Wissen@01", "kamesh@gmail.com", "9838292000", "wissen");
		loginDto = new LoginDTO("kamesh", "Wissen@01");
		user = new User(1, "kamesh", "Wissen@01", "kamesh@gmail.com", "9838292000", "wissen", new Date(), new Date(),
				new Date(), true, Collections.emptyList());

		refreshTokenStr = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9."
				+ "eyJzdWIiOiJrYW1lc2gxMSIsInRva2VuSWQiOjE0LCJpc3MiOiJSZWN1cklUIiwi"
				+ "ZXhwIjoxNjc0OTk3NjQ1LCJpYXQiOjE2NzQ4MjQ4NDV9.JzBSpPBEHHeapPN-"
				+ "SbXFAqXFbjP0Ew8CX0PCvXDDyGjIynlmTKpaXrGLYueXAZYcEQ7k5v1xReouTZgB5uI4JQ";
		refreshTokenEntity = new RefreshToken(1, 0, user.getUsername(), refreshTokenStr, null, true);
	}

	@Test
	void testSignup_Positive() throws JsonProcessingException, Exception {

		StatusDTO status = new StatusDTO(new Date().toString(), 201, "kamesh", "Account Created");
		ResponseEntity<StatusDTO> expected = new ResponseEntity<>(status, HttpStatus.CREATED);

		when(userService.saveUser(signUpDto)).thenReturn(expected);
		ResponseEntity<StatusDTO> result = userAccessManagmentController.signUp(signUpDto);

		assertEquals(HttpStatus.CREATED, result.getStatusCode());

	}

	@Test
	void testSignup_Negative() throws ValueAlreadyExistsException {

		StatusDTO status = new StatusDTO();
		ResponseEntity<StatusDTO> expected = new ResponseEntity<>(status, HttpStatus.BAD_REQUEST);

		when(userService.saveUser(signUpDto)).thenReturn(expected);
		ResponseEntity<StatusDTO> result = userAccessManagmentController.signUp(signUpDto);

		assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
	}

	@Test
	void testLogin_Positive() {

		when(authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())))
				.thenReturn(authentication);
		when((User) authentication.getPrincipal()).thenReturn(user);

		String refreshToken = jwtHelper.generateRefreshToken(user);
		when(jwtHelper.generateRefreshToken(user)).thenReturn(refreshToken);

		when(refreshTokenService.save(user, refreshToken)).thenReturn(refreshTokenEntity);

		ResponseEntity<TokenDTO> result = userAccessManagmentController.login(loginDto);

		assertEquals(HttpStatus.OK, result.getStatusCode());
	}

	@Test
	void testLogin_Negative() {

		when(authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())))
				.thenThrow(BadCredentialsException.class);

		assertThrows(BadCredentialsException.class, () -> userAccessManagmentController.login(loginDto));
	}

	@Test
	void testGenerateNewAccessToken_Positive()
			throws NotFoundException, UsernameNotFoundException, RefreshTokenNotValidException {
		TokenDTO tokendto = new TokenDTO(null, 0, null, null, refreshTokenStr);
		when(refreshTokenService.findByTokenValue(refreshTokenStr)).thenReturn(refreshTokenEntity);
		when(userService.loadUserByUsername(anyString())).thenReturn(user);
		ResponseEntity<TokenDTO> result = userAccessManagmentController.generateNewAccessToken(tokendto);
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}

	@Test
	void testGenerateNewAccessToken_Negative1() throws UsernameNotFoundException, RefreshTokenNotValidException {
		TokenDTO tokendto = new TokenDTO(null, 0, null, null, refreshTokenStr);
		when(refreshTokenService.findByTokenValue(refreshTokenStr)).thenThrow(RefreshTokenNotValidException.class);
		assertThrows(RefreshTokenNotValidException.class,
				() -> userAccessManagmentController.generateNewAccessToken(tokendto));
	}

	@Test
	void testGenerateNewAccessToken_Negative2() throws UsernameNotFoundException, RefreshTokenNotValidException {
		TokenDTO tokendto = new TokenDTO(null, 0, null, null, refreshTokenStr);
		when(refreshTokenService.findByTokenValue(refreshTokenStr)).thenReturn(refreshTokenEntity);
		when(userService.loadUserByUsername(null)).thenThrow(UsernameNotFoundException.class);
		assertThrows(UsernameNotFoundException.class,
				() -> userAccessManagmentController.generateNewAccessToken(tokendto));
	}

	@Test
	void testGenerateNewRefreshToken_Positive()
			throws NotFoundException, UsernameNotFoundException, RefreshTokenNotValidException {
		TokenDTO tokendto = new TokenDTO(null, 0, null, null, refreshTokenStr);
		when(refreshTokenService.findByTokenValue(refreshTokenStr)).thenReturn(refreshTokenEntity);
		when(userService.loadUserByUsername(anyString())).thenReturn(user);
		ResponseEntity<TokenDTO> result = userAccessManagmentController.generateNewRefreshToken(tokendto);
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}

	@Test
	void testGenerateNewRefreshToken_Negative1() throws UsernameNotFoundException, RefreshTokenNotValidException {
		TokenDTO tokendto = new TokenDTO(null, 0, null, null, refreshTokenStr);
		when(refreshTokenService.findByTokenValue(refreshTokenStr)).thenThrow(RefreshTokenNotValidException.class);
		assertThrows(RefreshTokenNotValidException.class,
				() -> userAccessManagmentController.generateNewRefreshToken(tokendto));
	}

	@Test
	void testGenerateNewRefreshToken_Negative2() throws UsernameNotFoundException, RefreshTokenNotValidException {
		TokenDTO tokendto = new TokenDTO(null, 0, null, null, refreshTokenStr);
		when(refreshTokenService.findByTokenValue(refreshTokenStr)).thenReturn(refreshTokenEntity);
		when(userService.loadUserByUsername(null)).thenThrow(UsernameNotFoundException.class);
		assertThrows(UsernameNotFoundException.class,
				() -> userAccessManagmentController.generateNewRefreshToken(tokendto));
	}

	@Test
	void testLogout_Positive() throws RefreshTokenNotValidException {
		TokenDTO tokendto = new TokenDTO(null, 0, null, null, refreshTokenStr);
		when(refreshTokenService.findByTokenValue(refreshTokenStr)).thenReturn(refreshTokenEntity);
		doNothing().when(refreshTokenService).delete(refreshTokenEntity);
		ResponseEntity<StatusDTO> result = userAccessManagmentController.logout(tokendto);
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}

	@Test
	void testLogout_Negative() throws RefreshTokenNotValidException {
		TokenDTO tokendto = new TokenDTO(null, 0, null, null, refreshTokenStr);
		when(refreshTokenService.findByTokenValue(refreshTokenStr)).thenThrow(RefreshTokenNotValidException.class);
		assertThrows(RefreshTokenNotValidException.class, () -> userAccessManagmentController.logout(tokendto));
	}

	@Test
	void testLogoutAll_Positive() throws RefreshTokenNotValidException {
		TokenDTO tokendto = new TokenDTO(null, 0, null, null, refreshTokenStr);
		when(refreshTokenService.findByTokenValue(refreshTokenStr)).thenReturn(refreshTokenEntity);
		doNothing().when(refreshTokenService).deleteByUsername(user.getUsername());
		ResponseEntity<StatusDTO> result = userAccessManagmentController.logoutAll(tokendto);
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}

	@Test
	void testLogoutAll_Negative() throws RefreshTokenNotValidException {
		TokenDTO tokendto = new TokenDTO(null, 0, null, null, refreshTokenStr);
		when(refreshTokenService.findByTokenValue(refreshTokenStr)).thenThrow(RefreshTokenNotValidException.class);
		assertThrows(RefreshTokenNotValidException.class, () -> userAccessManagmentController.logoutAll(tokendto));
	}
	
}
