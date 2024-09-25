package com.wissen.recruit.core.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO {

	@JsonInclude(Include.NON_NULL)
	private String timeStamp;
	
	@JsonInclude(Include.NON_NULL)
	private int status;
	
	@JsonInclude(Include.NON_NULL)
	private String message;
	
	@JsonInclude(Include.NON_NULL)
	private String accessToken;
	
	@NotBlank
	private String refreshToken;
	
	
}
