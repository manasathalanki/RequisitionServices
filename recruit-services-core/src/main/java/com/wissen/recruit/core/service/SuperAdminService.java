package com.wissen.recruit.core.service;

import com.wissen.recruit.core.entity.User;
import com.wissen.recruit.core.exception.NotFoundException;


public interface SuperAdminService {
	
	User createAdmin(User user);

	User resetPswd(String userId) throws NotFoundException;

	
}
