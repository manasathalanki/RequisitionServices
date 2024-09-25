package com.wissen.recruit.core.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wissen.recruit.core.dto.SignUpDTO;
import com.wissen.recruit.core.dto.StatusDTO;
import com.wissen.recruit.core.entity.Privilege;
import com.wissen.recruit.core.entity.Role;
import com.wissen.recruit.core.entity.User;
import com.wissen.recruit.core.exception.NotFoundException;
import com.wissen.recruit.core.exception.ValueAlreadyExistsException;
import com.wissen.recruit.core.repository.RoleRepository;
import com.wissen.recruit.core.repository.UserRepository;
import com.wissen.recruit.core.service.UserService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
		
	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Username not found"));
	}

	@Override
	public User loadUserByEmail(String email) throws NotFoundException {
		return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Email not found"));
	}

	@Override
	public User loadUserByPhone(String phone) throws NotFoundException {
		return userRepository.findByPhone(phone).orElseThrow(() -> new NotFoundException("Phone Number not found"));
	}

	public void validateUser(SignUpDTO dto) throws ValueAlreadyExistsException{

		try {
			User usernameCheck = loadUserByUsername(dto.getUsername());
			if (usernameCheck.getUsername().equals(dto.getUsername())) {
				log.error("SignUp - User Already Exists.Try different username..");
				throw new ValueAlreadyExistsException("User Already Exists.Try different username..");
			}
		} catch (UsernameNotFoundException e) {
			log.error(e.getMessage());
			log.error("SignUp - Username Check Passed.Proceeding to new user creation...");
		}

		try {
			User emailCheck = loadUserByEmail(dto.getEmail());
			if (emailCheck.getEmail().equals(dto.getEmail())) {
				log.error("SignUp - Email Already Exists.Try different Email..");
				throw new ValueAlreadyExistsException("Email Already Exists.Try different Email..");
			}
		} catch (NotFoundException e) {
			log.error(e.getMessage());
			log.error("SignUp - Email Check Passed.Proceeding to new user creation...");
		}

		try {
			User phoneCheck = loadUserByPhone(dto.getPhone());
			if (phoneCheck.getPhone().equals(dto.getPhone())) {
				log.error("SignUp - Phone Already Exists.Try different Phone..");
				throw new ValueAlreadyExistsException("Phone Already Exists.Try different Phone..");
			}
		} catch (NotFoundException e) {
			log.error(e.getMessage());
			log.error("SignUp - Phone Check Passed.Proceeding to new user creation...");
		}

	}

	@Override
	public User findById(String id) {
		return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("UserID not found"));
	}

	@SuppressWarnings("unused")
	public List<String> getPrivileges(Collection<Role> roles) {
		List<String> privileges = new ArrayList<>();
		List<Privilege> collection = new ArrayList<>();

		for (Role role : roles) {
			privileges.add(role.getName());
			collection.addAll(role.getPrivileges());
		}

		for (Privilege item : collection) {
			privileges.add(item.getName());
		}

		return privileges;
	}

	@SuppressWarnings("unused")
	public Collection<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (String privilege : privileges) {
			authorities.add(new SimpleGrantedAuthority(privilege));
		}
		return authorities;
	}

	@Override
	public ResponseEntity<StatusDTO> saveUser(SignUpDTO dto) throws ValueAlreadyExistsException {
		
		validateUser(dto);
		User newUser = new User();
		newUser.setUsername(dto.getUsername());
		newUser.setPassword(passwordEncoder.encode(dto.getPassword()));
		log.info(passwordEncoder.encode(dto.getPassword()));
		newUser.setEmail(dto.getEmail());
		newUser.setPhone(dto.getPhone());
		newUser.setFirmname(dto.getFirmname());
		newUser.setCreatedDate(new Date());
		newUser.setLastLoggedInDate(new Date());
		newUser.setModifiedDate(new Date());
		newUser.setEnabled(true);
		newUser.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
		log.info("SignUp - Saving User Details in User DB");
		userRepository.save(newUser);
		log.info("SignUp - Saved User Details in User DB");
		
		log.info("SignUp - New User Account - " + dto.getUsername() + " Created with default role - ROLE_USER");
		return new ResponseEntity<>(new StatusDTO(new Date().toString(),HttpServletResponse.SC_CREATED,dto.getUsername(), "Account Created"),HttpStatus.CREATED);
		
	}

}
