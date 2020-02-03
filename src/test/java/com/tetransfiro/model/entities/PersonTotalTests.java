package com.tetransfiro.model.entities;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PersonTotalTests {

	private static final BigDecimal ANY_NEGATIVE_BIG_DECIMAL_VALUE = BigDecimal.valueOf(-1);
	private static final String FULL_NAME = "Full Name";
	private static final String INVALID_EMAIL = "@.com";
	private static final String VALID_EMAIL = "email@test.com";
	private static final BigDecimal ANY_BIG_DECIMAL_VALUE = BigDecimal.ONE;

	@Test
	public void shouldThrowConstraintViolationExceptionWhenPersonFullNameIsNull() {
		assertThrows(ConstraintViolationException.class,
		             () -> new PersonTotal(null, VALID_EMAIL, ANY_BIG_DECIMAL_VALUE));
	}

	@Test
	public void shouldThrowConstraintViolationExceptionWhenPersonFullNameIsEmpty() {
		assertThrows(ConstraintViolationException.class,
		             () -> new PersonTotal(" ", VALID_EMAIL, ANY_BIG_DECIMAL_VALUE));
	}

	@Test
	public void shouldThrowConstraintViolationExceptionWhenEmailIsNull() {
		assertThrows(ConstraintViolationException.class,
		             () -> new PersonTotal(FULL_NAME, null, ANY_BIG_DECIMAL_VALUE));
	}

	@Test
	public void shouldThrowConstraintViolationExceptionWhenEmailIsNotValid() {
		assertThrows(ConstraintViolationException.class,
		             () -> new PersonTotal(FULL_NAME, INVALID_EMAIL, ANY_BIG_DECIMAL_VALUE));
	}
	
	@Test
	public void shouldThrowConstraintViolationExceptionWhenTotalIsNull() {
		assertThrows(ConstraintViolationException.class,
		             () -> new PersonTotal(FULL_NAME, VALID_EMAIL, null));
	}
	
	@Test
	public void shouldThrowConstraintViolationExceptionWhenTotalIsLessThanZero() {
		assertThrows(ConstraintViolationException.class,
		             () -> new PersonTotal(FULL_NAME, VALID_EMAIL, ANY_NEGATIVE_BIG_DECIMAL_VALUE));
	}
}
