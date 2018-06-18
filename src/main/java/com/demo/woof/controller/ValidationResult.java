package com.demo.woof.controller;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {

	private List<ErrorDetail> errors = new ArrayList<>();

	public List<ErrorDetail> getErrors() {
		return errors;
	}
	
	public void setErrors(List<ErrorDetail> errors) {
		this.errors = errors;
	}
}
