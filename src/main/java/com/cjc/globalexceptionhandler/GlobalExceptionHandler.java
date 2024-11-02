package com.cjc.globalexceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	
	
	@ExceptionHandler(NumberFormatException.class)
	public ResponseEntity<String> handleNumberFormatException(NumberFormatException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insert Number");
	}
	
	
	
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public ResponseEntity<List<String>> handleValidationException(MethodArgumentNotValidException ex) {
//		BindingResult result = ex.getBindingResult();
//		List<String> errorMessages = new ArrayList<>();
//
//		result.getFieldErrors().forEach(fieldError -> {
//			errorMessages.add(fieldError.getDefaultMessage());
//		});
//
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
//	}
//
//	@ExceptionHandler(ConstraintViolationException.class)
//	public ResponseEntity<List<String>> handleConstraintViolationException(ConstraintViolationException ex) {
//		Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
//		List<String> errorMessages = new ArrayList<>();
//
//		violations.forEach(violation -> {
//			errorMessages.add(violation.getMessage());
//		});
//
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
//	}
}
