package com.tetransfiro.model;

import javax.validation.ConstraintViolationException;

import com.tetransfiro.utils.IntegrityValidator;

public class ValidatedData {
	
	public void validate() {
		IntegrityValidator.validate(this)
		                  .ifPresent(violations -> { throw new ConstraintViolationException(violations); });
	}
	
	public void validate(Object object) {
		IntegrityValidator.validate(object)
		                  .ifPresent(violations -> { throw new ConstraintViolationException(violations); });
	}
}
