package com.wissen.recruit.requisition.exception;

import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExceptionMessage {
	private String errMsg;
	private String errCode;
	private Map<String, String> validationMessages;
	
	public ExceptionMessage(String errMsg, String errCode) {
		super();
		this.errMsg = errMsg;
		this.errCode = errCode;
	}
	

}
