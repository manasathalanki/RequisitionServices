package com.wissen.recruit.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException {

	private static final long serialVersionUID = 6667261402798225624L;

	public ForbiddenException(String message) {
		super(message);
	}

}
