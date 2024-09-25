package com.wissen.recruit.requisition.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UsernameNotFoundException extends Exception {

	private static final long serialVersionUID = 8131218735691350740L;

	public UsernameNotFoundException(String message) {
		super(message);
	}

}
