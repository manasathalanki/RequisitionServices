package com.wissen.recruit.core.constants;

public class RegExpressions {

	public static final String USERNAME_REGEX = "[\\w]+";
	public static final String PASS_REGEX = "^(?=.*[0-9])"
            + "(?=.*[a-z])(?=.*[A-Z])"
            + "(?=.*[@#$%^&+=])"
            + "(?=\\S+$).{8,20}$";
	public static final String EMAIL_REGEX = "[a-zA-Z0-9_.\\-]+[@][a-zA-Z]+[.][a-z]{2,3}";
	public static final String PHONE_REGEX = "[6-9][0-9]{9}";
	
	private RegExpressions() {
	}
	

}
