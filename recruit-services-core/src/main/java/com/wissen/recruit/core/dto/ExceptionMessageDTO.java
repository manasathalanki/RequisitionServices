package com.wissen.recruit.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionMessageDTO {
	private String timeStamp;
	private int status;
	private String errMsg;
	private String path;
}
