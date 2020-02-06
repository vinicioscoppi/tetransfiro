package com.tetransfiro.model.exceptions;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class TTConstraintViolationException extends ConstraintViolationException {

	private static final long serialVersionUID = 1L;

	public TTConstraintViolationException(Set<? extends ConstraintViolation<?>> constraintViolations) {
		super(constraintViolations);
	}
}
