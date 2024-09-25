package com.wissen.recruit.core.controller;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.security.Principal;
import java.util.Collections;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.wissen.recruit.core.dto.UserInfoDTO;
import com.wissen.recruit.core.entity.User;
import com.wissen.recruit.core.service.UserService;

@ExtendWith(MockitoExtension.class)
class UserResourceControllerTest {

	@InjectMocks
	UserResourceController userResourceController;

	@Mock
	UserService userService;

	@Mock
	Principal principal;

	User user;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		user = new User(1, "kamesh", "Wissen@01", "kamesh@gmail.com", "9838292000", "wissen", new Date(), new Date(),
				new Date(), true, Collections.emptyList());
	}

	@Test
	void testUserInfo_Positive() {
		when(userService.loadUserByUsername(principal.getName())).thenReturn(user);
		ResponseEntity<UserInfoDTO> result = userResourceController.userInfo(principal);
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	
	@Test
	void testUserInfo_Negative() {
		when(userService.loadUserByUsername(principal.getName())).thenThrow(UsernameNotFoundException.class);
		assertThrows(UsernameNotFoundException.class,
				() -> userResourceController.userInfo(principal));
	}
}
