package com.wissen.recruit.core.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.wissen.recruit.core.constants.RegExpressions;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SignUpDTO {

	@NotBlank
	@Size(min=5,max=10)
	@Pattern(regexp = RegExpressions.USERNAME_REGEX,message="Username should be lowercase characters and numeric characters")
	private String username;
	
	@NotBlank
	@Size(min=8,max=16)
	@Pattern(regexp=RegExpressions.PASS_REGEX,message="Password should contain UpperCase,LowerCase and Special Characters(._@#$%&\\\\*!)")
	private String password;
	
	@NotBlank
	@Size(min=5,max=20)
	@Pattern(regexp=RegExpressions.EMAIL_REGEX,message="Email should contain @ and end with .com or .in or .net")
	private String email;
	
	@NotBlank
	@Size(min=10,max=10)
	@Pattern(regexp=RegExpressions.PHONE_REGEX,message="Phone number should be valid 10 digits long Indian Mobile Number")
	private String phone;
	
	@NotBlank
	@Size(min=1,max=10)
	private String firmname;

}
