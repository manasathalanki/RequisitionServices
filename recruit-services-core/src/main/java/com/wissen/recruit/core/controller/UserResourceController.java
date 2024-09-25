package com.wissen.recruit.core.controller;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wissen.recruit.core.dto.UserInfoDTO;
import com.wissen.recruit.core.entity.Role;
import com.wissen.recruit.core.entity.User;
import com.wissen.recruit.core.service.UserService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/resources")
public class UserResourceController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/userinfo")
	public ResponseEntity<UserInfoDTO> userInfo(Principal principal) {
		User user = userService.loadUserByUsername(principal.getName());
		log.info("Userinfo - Returning UserDetails in JSON...");
		List<String> roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
		return ResponseEntity.ok(new UserInfoDTO(new Date().toString(),HttpServletResponse.SC_OK,user.getUsername(), user.getPhone(), user.getEmail(),
				user.getFirmname(),roles, user.getLastLoggedInDate(), user.getModifiedDate(),user.getCreatedDate()));
	}
	
	
}
