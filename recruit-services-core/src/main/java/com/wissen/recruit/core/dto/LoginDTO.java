package com.wissen.recruit.core.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.wissen.recruit.core.constants.RegExpressions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginDTO {

	@NotBlank
	@Size(min = 5,max =10)
	@Pattern(regexp = RegExpressions.USERNAME_REGEX,message="username should be lowercase characters and numeric characters")
	private String username;

	@NotBlank
	@Size(min = 8, max = 16)
	@Pattern(regexp=RegExpressions.PASS_REGEX,message="Password should contain UpperCase,LowerCase and Special Characters(._@#$%&\\*!)")
	private String password;

}
