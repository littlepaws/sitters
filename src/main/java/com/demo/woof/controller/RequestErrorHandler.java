package com.demo.woof.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.demo.woof.ValidationConst;

@ControllerAdvice
public class RequestErrorHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ValidationResult getFieldErrors(MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
		List<FieldError> fieldErrors = result.getFieldErrors();
		ValidationResult errors = new ValidationResult();
		
		for (FieldError fieldErr: fieldErrors) {
			ErrorDetail err = new ErrorDetail(fieldErr.getField(), fieldErr.getDefaultMessage());
			errors.getErrors().add(err);
		}
		return errors;
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ValidationResult getError(HttpMessageNotReadableException ex) {

		ValidationResult errors = new ValidationResult();
		ErrorDetail err;
		
		if (ex.getMessage().contains(ValidationConst.EMPTY_REQUEST_BODY_ERR)) {		
			err = new ErrorDetail(ValidationConst.REQUEST_BODY_ERR, ValidationConst.EMPTY_REQUEST_BODY_ERR);
		} else {
			err = new ErrorDetail(ValidationConst.REQUEST_BODY_ERR, ValidationConst.MESSAGE_BODY_UNREADABLE_ERR);
		}
		errors.getErrors().add(err);
		return errors;
	}
	
	
}
