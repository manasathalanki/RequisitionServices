package com.wissen.recruit.core.exception;

@SuppressWarnings("serial")
public class RefreshTokenNotValidException extends Exception {

	public RefreshTokenNotValidException(String message) {
		super(message);
	}
}
