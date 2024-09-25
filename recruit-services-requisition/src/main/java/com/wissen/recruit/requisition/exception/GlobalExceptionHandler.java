package com.wissen.recruit.requisition.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.wissen.recruit.requisition.constants.ErrorCodes;
import com.wissen.recruit.requisition.constants.ErrorMessages;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(value = { InvalidIdException.class })
	public ResponseEntity<ExceptionMessage> invalidRequisitionId(InvalidIdException exception) {
		return new ResponseEntity<>(new ExceptionMessage(exception.getMessage(), ErrorCodes.INVALID_REQUISITION_ID), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { RequisitionNotFoundException.class })
	public ResponseEntity<ExceptionMessage> requisitionDetailsNotFound(RequisitionNotFoundException exception) {
		return new ResponseEntity<>(new ExceptionMessage(exception.getMessage(), ErrorCodes.REQUISTION_DETAILS_NOT_FOUND), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<ExceptionMessage> defaultExcentionHandler(Exception exception) {
		return new ResponseEntity<>(new ExceptionMessage(exception.getMessage(), String.valueOf(HttpStatus.BAD_REQUEST.value())), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { CandidateNotFoundException.class })
	public ResponseEntity<ExceptionMessage> handleCandidateNotFoundException(CandidateNotFoundException exception) {
		return new ResponseEntity<>(new ExceptionMessage(exception.getMessage(), String.valueOf(HttpServletResponse.SC_NOT_FOUND)), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = { UsernameNotFoundException.class })
	public ResponseEntity<ExceptionMessage> handleUsernameNotFoundException(UsernameNotFoundException exception) {
		return new ResponseEntity<>(new ExceptionMessage(exception.getMessage(), String.valueOf(HttpServletResponse.SC_NOT_FOUND)), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = { WorkflowNotFoundException.class })
	public ResponseEntity<ExceptionMessage> WorkflowNotFound(WorkflowNotFoundException exception) {
		return new ResponseEntity<>(new ExceptionMessage(exception.getMessage(), ErrorCodes.WORKFLOW_NOT_FOUND), HttpStatus.NOT_FOUND);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		 Map<String,String> errors=new HashMap<>();
		 ex.getBindingResult().getAllErrors().forEach(
				 error ->{
					 String fieldName= ((FieldError) error).getField();
					 String message=error.getDefaultMessage();
					 errors.put(fieldName, message); 
				 });
		 ExceptionMessage message=new ExceptionMessage();
		 message.setValidationMessages(errors);
		 message.setErrCode(ErrorCodes.CONSTRAINT_VALIDATION_ERROR);
		 message.setErrMsg(ErrorMessages.CONSTRAINT_VALIDATION_FAILED);
		 
		 return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}
	
}
