package com.wissen.recruit.core.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.wissen.recruit.core.entity.RefreshToken;

@Transactional
@ActiveProfiles(value = "qa")
@SpringBootTest
class RefreshTokenRepositoryTest {

	@Autowired
	RefreshTokenRepository refreshTokenRepo;

	RefreshToken refreshTokenEntity;

	String refreshTokenStr;

	@BeforeEach
	void setup() throws Exception {
		refreshTokenStr = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9."
				+ "eyJzdWIiOiJrYW1lc2gxIiwidG9rZW5JZCI6OSwiaXNzIjoiUmV"
				+ "jdXJJVCIsImV4cCI6MTY3NTM0NzIxNSwiaWF0IjoxNjc1MTc0NDE1fQ."
				+ "hfMmGVmUZ2EZz3snF3wDGObTueAf9nqNzTg4w6x10YjEYeGUm4nYpM1d0C4ettp6-hGcvJDCecLNHNuPCL0TVg";
		refreshTokenEntity = new RefreshToken(1, 0, "kamesh", refreshTokenStr, new Date(), true);
		refreshTokenRepo.save(refreshTokenEntity);
	}

	@AfterEach
	void destroyAll() {
		refreshTokenRepo.deleteAll();
	}

	@Test
	@DisplayName(value = "Positive Test for DeleteByUsername Method in RefreshToken Repo")
	void testDeleteByUsername_Positive() {
		assertDoesNotThrow(() -> refreshTokenRepo.deleteByUsername(refreshTokenEntity.getUsername()));
	}

	@Test
	@DisplayName(value = "Negative Test for DeleteByUsername Method in RefreshToken Repo")
	void testDeleteByUsername_Negative() {
		assertDoesNotThrow(() -> refreshTokenRepo.deleteByUsername("kamesh1"));
	}

	@Test
	@DisplayName(value = "Positive Test for DeleteByTokenValue Method in RefreshToken Repo")
	void testDeleteByTokenValue_Positive() {
		assertDoesNotThrow(() -> refreshTokenRepo.deleteByTokenValue(refreshTokenStr));
	}

	@Test
	@DisplayName(value = "Negative Test for DeleteByTokenValue Method in RefreshToken Repo")
	void testDeleteByValue_Negative() {
		assertDoesNotThrow(() -> refreshTokenRepo.deleteByTokenValue("kamesh1"));
	}

	@Test
	@DisplayName(value = "Positive Test for FindByTokenValue Method in RefreshToken Repo")
	void testFindByTokenValue_Positive() {
		Optional<RefreshToken> foundToken = refreshTokenRepo.findByTokenValue(refreshTokenStr);
		assertThat(foundToken).isPresent();
	}

	@Test
	@DisplayName(value = "Negative Test for FindByTokenValue Method in RefreshToken Repo")
	void testFindByUsername_Negative() {
		Optional<RefreshToken> foundToken = refreshTokenRepo.findByTokenValue("kamesh1");
		assertThat(foundToken).isEmpty();
	}

	@Test
	@DisplayName(value = "Positive Test for FindById Method in RefreshToken Repo")
	void testFindById_Positive() {
		Optional<RefreshToken> foundToken = refreshTokenRepo.findById(7);
		assertThat(foundToken).isPresent();
	}

	@Test
	@DisplayName(value = "Negative Test for FindById Method in RefreshToken Repo")
	void testFindById_Negative() {
		Optional<RefreshToken> foundToken = refreshTokenRepo.findById(2);
		assertThat(foundToken).isEmpty();
	}

}
