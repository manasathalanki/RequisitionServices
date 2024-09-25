package com.wissen.recruit.core.serviceimpl;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.wissen.recruit.core.entity.RefreshToken;
import com.wissen.recruit.core.entity.User;
import com.wissen.recruit.core.exception.RefreshTokenNotValidException;
import com.wissen.recruit.core.jwt.JwtHelper;
import com.wissen.recruit.core.repository.RefreshTokenRepository;
import com.wissen.recruit.core.service.impl.RefreshTokenServiceImpl;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RefreshTokenServiceImplTest {

	@InjectMocks
	RefreshTokenServiceImpl refreshTokenServiceImpl;

	@Mock
	RefreshTokenRepository refreshTokenRepository;

	@Mock
	JwtHelper jwtHelper;

	String refreshTokenStr;

	RefreshToken refreshTokenEntity;

	User user;

	@BeforeEach
	void setup() {

		MockitoAnnotations.openMocks(this);

		user = new User(1, "kamesh", "Wissen@01", "kamesh@gmail.com", "9838292000", "wissen", new Date(), new Date(),
				new Date(), true, Collections.emptyList());
		refreshTokenStr = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9."
				+ "eyJzdWIiOiJrYW1lc2gxMSIsInRva2VuSWQiOjE0LCJpc3MiOiJSZWN"
				+ "1cklUIiwiZXhwIjoxNjc1MDA4MzYxLCJpYXQiOjE2NzQ4MzU1NjF9."
				+ "KkP8MXnK_BMW_JZrZkXTZkzIjF3yN62FEpeW_n9KhpGKT1VdXLK5KGEw9a6HQtwd9F16SzMONBACIOxfLSPdqA";
		refreshTokenEntity = new RefreshToken(1, 0, user.getUsername(), refreshTokenStr, new Date(), true);
	}

	@Test
	void testFindByTokenValue_Positive() throws RefreshTokenNotValidException {
		when(refreshTokenRepository.findByTokenValue(any())).thenReturn(Optional.of(refreshTokenEntity));
		when(jwtHelper.validateRefreshToken(refreshTokenStr)).thenReturn(true);
		assertEquals(refreshTokenEntity, refreshTokenServiceImpl.findByTokenValue(refreshTokenStr));
	}

	@Test
	void testFindByTokenValue_Negative1() throws RefreshTokenNotValidException {
		when(refreshTokenRepository.findByTokenValue(refreshTokenStr)).thenReturn(null);
		assertThrows(RefreshTokenNotValidException.class,
				() -> refreshTokenServiceImpl.findByTokenValue(refreshTokenStr));
	}

	@Test
	void testFindByTokenValue_Negative2() throws RefreshTokenNotValidException {
		when(refreshTokenRepository.findByTokenValue(refreshTokenStr)).thenReturn(Optional.of(refreshTokenEntity));
		when(jwtHelper.validateRefreshToken(refreshTokenStr)).thenReturn(false);
		assertThrows(RefreshTokenNotValidException.class,
				() -> refreshTokenServiceImpl.findByTokenValue(refreshTokenStr));
	}

	@Test
	void testDeleteByUsername_Positive() {
		doNothing().when(refreshTokenRepository).deleteByUsername(user.getUsername());
		assertDoesNotThrow(() -> refreshTokenServiceImpl.deleteByUsername(user.getUsername()));
	}

	@Test
	void testDelete_Positive() {
		doNothing().when(refreshTokenRepository).delete(refreshTokenEntity);
		assertDoesNotThrow(() -> refreshTokenServiceImpl.delete(refreshTokenEntity));
	}

	@Test
	void testSave_Positive() {
		when(refreshTokenRepository.save(any())).thenReturn(refreshTokenEntity);
		RefreshToken actual = refreshTokenServiceImpl.save(user, refreshTokenStr);
		assertEquals(refreshTokenEntity, actual);
	}

	@Test
	void testModify_Positive() {
		refreshTokenEntity.setUsername("kamesh2");
		when(refreshTokenRepository.save(refreshTokenEntity)).thenReturn(refreshTokenEntity);
		assertDoesNotThrow(() -> refreshTokenServiceImpl.modify(refreshTokenEntity));
	}

}
