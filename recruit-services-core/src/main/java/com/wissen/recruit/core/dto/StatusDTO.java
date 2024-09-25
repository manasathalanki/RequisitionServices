package com.wissen.recruit.core.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StatusDTO {

	private String timeStamp;
	private int status;
	@JsonInclude(Include.NON_NULL)
	private String username;
	private String message;
	
	
}
