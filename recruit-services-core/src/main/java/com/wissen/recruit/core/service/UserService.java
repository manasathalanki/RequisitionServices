package com.wissen.recruit.core.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.wissen.recruit.core.dto.SignUpDTO;
import com.wissen.recruit.core.dto.StatusDTO;
import com.wissen.recruit.core.entity.User;
import com.wissen.recruit.core.exception.NotFoundException;
import com.wissen.recruit.core.exception.ValueAlreadyExistsException;

public interface UserService extends UserDetailsService {

	User findById(String userId);

	User loadUserByUsername(String username) throws UsernameNotFoundException;

	User loadUserByEmail(String email) throws NotFoundException;

	User loadUserByPhone(String phone) throws NotFoundException;

	void validateUser(SignUpDTO dto) throws ValueAlreadyExistsException;

	ResponseEntity<StatusDTO> saveUser(SignUpDTO dto) throws ValueAlreadyExistsException;

}
