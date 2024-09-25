package com.wissen.recruit.core.advice;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.wissen.recruit.core.dto.ExceptionMessageDTO;
import com.wissen.recruit.core.exception.RefreshTokenNotValidException;
import com.wissen.recruit.core.exception.ValueAlreadyExistsException;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = { MethodArgumentNotValidException.class })
	public Map<String, String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
		Map<String, String> errorMap = new HashMap<>();
		ex.getBindingResult().getFieldErrors().stream().forEach(x -> {
			errorMap.put(x.getField(), x.getDefaultMessage());
			log.error(x.getDefaultMessage());
		});
		return errorMap;
	}

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(value = { BadCredentialsException.class })
	public ResponseEntity<ExceptionMessageDTO> handleBadCredentials(BadCredentialsException ex, WebRequest request) {
		return new ResponseEntity<>(helperFunction(ex, HttpServletResponse.SC_UNAUTHORIZED, request),
				HttpStatus.UNAUTHORIZED);
	}

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(value = { RefreshTokenNotValidException.class })
	public ResponseEntity<ExceptionMessageDTO> handleRefreshTokenNotValid(RefreshTokenNotValidException ex,
			WebRequest request) {
		return new ResponseEntity<>(helperFunction(ex, HttpServletResponse.SC_UNAUTHORIZED, request),
				HttpStatus.UNAUTHORIZED);
	}

	@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
	@ExceptionHandler(value = { ValueAlreadyExistsException.class })
	public ResponseEntity<ExceptionMessageDTO> handleValueAlreadyExists(ValueAlreadyExistsException ex,
			WebRequest request) {
		return new ResponseEntity<>(helperFunction(ex, HttpServletResponse.SC_NOT_ACCEPTABLE, request),
				HttpStatus.NOT_ACCEPTABLE);
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(value = { UsernameNotFoundException.class })
	public ResponseEntity<ExceptionMessageDTO> handleUsernameNotFound(UsernameNotFoundException ex,
			WebRequest request) {
		return new ResponseEntity<>(helperFunction(ex, HttpServletResponse.SC_NOT_FOUND, request),
				HttpStatus.NOT_FOUND);
	}

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(value = { InsufficientAuthenticationException.class })
	public ResponseEntity<ExceptionMessageDTO> handleForbidden(InsufficientAuthenticationException ex,
			HttpServletResponse response, WebRequest request) {
		return new ResponseEntity<>(helperFunction(ex, HttpServletResponse.SC_UNAUTHORIZED, request),
				HttpStatus.UNAUTHORIZED);
	}

	public ExceptionMessageDTO helperFunction(Exception ex, int responseCode, WebRequest request) {
		ExceptionMessageDTO dto = new ExceptionMessageDTO(new Date().toString(), responseCode, ex.getMessage(),
				request.getDescription(false));
		log.error(ex.getMessage());
		return dto;
	}
}
