package com.wissen.recruit.core.constants;

import lombok.Data;

@Data
public final class ErrorCodes {
	//user error codes
	public static final String USER_NOT_EXISTS = "101";
	public static final String INVALID_USER = "102";
	private ErrorCodes() {
	}

}
