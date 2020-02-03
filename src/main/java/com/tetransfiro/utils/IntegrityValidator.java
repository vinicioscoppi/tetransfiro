package com.tetransfiro.utils;

import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IntegrityValidator {

	public static <T> Optional<Set<ConstraintViolation<T>>> validate(T object) {
		var validationErrors = Validation.buildDefaultValidatorFactory().getValidator().validate(object);
		return Optional.ofNullable(validationErrors.isEmpty() ? null : validationErrors);
	}
}
