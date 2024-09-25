package com.wissen.recruit.core.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.wissen.recruit.core.dto.SignUpDTO;
import com.wissen.recruit.core.dto.StatusDTO;
import com.wissen.recruit.core.entity.Privilege;
import com.wissen.recruit.core.entity.Role;
import com.wissen.recruit.core.entity.User;
import com.wissen.recruit.core.exception.NotFoundException;
import com.wissen.recruit.core.exception.ValueAlreadyExistsException;
import com.wissen.recruit.core.repository.RoleRepository;
import com.wissen.recruit.core.repository.UserRepository;
import com.wissen.recruit.core.service.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceImplTest {

	@InjectMocks
	UserServiceImpl userServiceImpl;

	@Mock
	UserRepository userRepository;

	@Mock
	RoleRepository roleRepository;

	@Mock
	PasswordEncoder passwordEncoder;

	User user;

	SignUpDTO signUpDto;

	String timeStamp;
	
	Role role;
	Privilege privileges;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		user = new User(1, "kamesh", "Wissen@01", "kamesh@gmail.com", "9838292000", "wissen", new Date(), new Date(),
				new Date(), true, Collections.emptyList());
		signUpDto = new SignUpDTO("kamesh", "Wissen@01", "kamesh@gmail.com", "9838292000", "wissen");
		timeStamp = new Date().toString();
	}

	@Test
	void testLoadByUsername_Positive() {
		when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
		assertEquals(user, userServiceImpl.loadUserByUsername("kamesh"));
	}

	@Test
	void testLoadByUsername_Negative() {
		when(userRepository.findByUsername(null)).thenReturn(Optional.empty());
		assertThrows(UsernameNotFoundException.class, () -> userServiceImpl.loadUserByUsername(null));
	}

	@Test
	void testLoadByEmail_Positive() throws NotFoundException {
		when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
		assertEquals(user, userServiceImpl.loadUserByEmail("kamesh@gmail.com"));
	}

	@Test
	void testLoadByEmail_Negative() {
		when(userRepository.findByEmail(null)).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> userServiceImpl.loadUserByEmail(null));
	}

	@Test
	void testLoadByPhone_Positive() throws NotFoundException {
		when(userRepository.findByPhone(any())).thenReturn(Optional.of(user));
		assertEquals(user, userServiceImpl.loadUserByPhone("9838292000"));
	}

	@Test
	void testLoadByPhone_Negative() {
		when(userRepository.findByPhone(null)).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> userServiceImpl.loadUserByPhone(null));
	}

	@Test
	void testFindById_Positive() {
		when(userRepository.findById(any())).thenReturn(Optional.of(user));
		assertEquals(user, userServiceImpl.findById("1"));
	}

	@Test
	void testFindById_Negative() {
		when(userRepository.findById(null)).thenReturn(Optional.empty());
		assertThrows(UsernameNotFoundException.class, () -> userServiceImpl.loadUserByUsername(null));
	}

	@Test
	void testValidateUser_Positive() throws NotFoundException, ValueAlreadyExistsException {
		when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
		when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
		when(userRepository.findByPhone(any())).thenReturn(Optional.empty());
		assertDoesNotThrow(() -> userServiceImpl.validateUser(signUpDto));
	}

	@Test
	void testValidateUser_Negative1() throws NotFoundException, ValueAlreadyExistsException {
		when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
		assertThrows(ValueAlreadyExistsException.class, () -> userServiceImpl.validateUser(signUpDto));
	}

	@Test
	void testValidateUser_Negative2() throws NotFoundException, ValueAlreadyExistsException {
		when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
		when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
		assertThrows(ValueAlreadyExistsException.class, () -> userServiceImpl.validateUser(signUpDto));
	}

	@Test
	void testValidateUser_Negative3() throws NotFoundException, ValueAlreadyExistsException {
		when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
		when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
		when(userRepository.findByPhone(any())).thenReturn(Optional.of(user));
		assertThrows(ValueAlreadyExistsException.class, () -> userServiceImpl.validateUser(signUpDto));
	}

	@Test
	void testSaveUser_Positive() throws ValueAlreadyExistsException {

		StatusDTO status = new StatusDTO(timeStamp, 201, "kamesh", "Account Created");
		ResponseEntity<StatusDTO> expected = new ResponseEntity<>(status, HttpStatus.CREATED);

		when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
		when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
		when(userRepository.findByPhone(any())).thenReturn(Optional.empty());

		when(roleRepository.findByName("ROLE_USER")).thenReturn(new Role("ROLE_USER"));
		when(userRepository.save(any())).thenReturn(user);

		assertEquals(expected, userServiceImpl.saveUser(signUpDto));

	}

	@Test
	void testSaveUser_Negative() throws ValueAlreadyExistsException {

		when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
		assertThrows(ValueAlreadyExistsException.class, () -> userServiceImpl.saveUser(signUpDto));
	}

}
