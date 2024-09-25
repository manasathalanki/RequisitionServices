package com.wissen.recruit.requisition.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class WorkflowNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1951534891736038256L;

	public WorkflowNotFoundException(String message) {
		super(message);
	}
}
