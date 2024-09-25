package com.wissen.recruit.core.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.wissen.recruit.core.entity.User;

@ActiveProfiles(value = "qa")
@SpringBootTest
class UserRepositoryTest {

	@Autowired
	UserRepository userRepo;

	private User user;

	@BeforeEach
	void setup() {
		user = new User(1, "kamesh", "Wissen@01", "kamesh@gmail.com", "9838292000", "wissen", new Date(), new Date(),
				new Date(), true, Collections.emptyList());
		userRepo.save(user);
	}

	@AfterEach
	void destroyAll() {
		userRepo.deleteAll();
	}

	@Test
	@DisplayName(value = "Positive Test for FindByUsername Method in User Repo")
	void testFindByUsername_Positive() {
		Optional<User> foundUser = userRepo.findByUsername("kamesh");
		assertThat(foundUser).isPresent();
	}

	@Test
	@DisplayName(value = "Negative Test for FindByUsername Method in User Repo")
	void testFindByUsername_Negative() {
		Optional<User> foundUser = userRepo.findByUsername("kamesh1");
		assertThat(foundUser).isEmpty();
	}

	@Test
	@DisplayName(value = "Positive Test for FindByEmail Method in User Repo")
	void testFindByEmail_Positive() {
		Optional<User> foundUser = userRepo.findByEmail("kamesh@gmail.com");
		assertThat(foundUser).isPresent();
	}

	@Test
	@DisplayName(value = "Negative Test for FindByEmail Method in User Repo")
	void testFindByEmail_Negative() {
		Optional<User> foundUser = userRepo.findByEmail("kamesh");
		assertThat(foundUser).isEmpty();
	}

	@Test
	@DisplayName(value = "Positive Test for FindByPhone Method in User Repo")
	void testFindByPhone_Positive() {
		Optional<User> foundUser = userRepo.findByPhone("9838292000");
		assertThat(foundUser).isPresent();
	}

	@Test
	@DisplayName(value = "Negative Test for FindByPhone Method in User Repo")
	void testFindByPhone_Negative() {
		Optional<User> foundUser = userRepo.findByPhone("kamesh");
		assertThat(foundUser).isEmpty();
	}

}
