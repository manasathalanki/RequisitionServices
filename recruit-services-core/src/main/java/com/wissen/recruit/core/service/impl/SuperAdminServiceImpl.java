package com.wissen.recruit.core.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wissen.recruit.core.entity.User;
import com.wissen.recruit.core.repository.SuperAdminRepository;
import com.wissen.recruit.core.service.SuperAdminService;

@Service
public class SuperAdminServiceImpl implements SuperAdminService {
	Logger logger = LoggerFactory.getLogger(SuperAdminServiceImpl.class);
	
	@Autowired
	SuperAdminRepository superAdminRepository;

	@Override
	public User createAdmin(User user) {
		return superAdminRepository.save(user);
	}

	@Override
	public User resetPswd(String userId) {
		logger.info("super admin trying to reset default password to the user with userId - {}",userId);
		Optional<User> user = superAdminRepository.findById(userId);
		if (user.isPresent()) {
			User userObj = user.get();
			userObj.setPassword("default_password");
			superAdminRepository.save(userObj);
			return userObj;
		}
		// check whether user is active or not if active then only we will reset his
		// password to default or else we will throw exception
		return null;
	}

}
