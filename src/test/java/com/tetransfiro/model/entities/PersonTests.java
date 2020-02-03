package com.tetransfiro.model.entities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PersonTests {

	private static final String EXPECTED_FULL_NAME = "First Last";
	private static final String FIRST_NAME = "First";
	private static final String LAST_NAME = "Last";
	private static final String EMAIL = "email@test.com";
	private static final String INVALID_EMAIL = "@.com";
	private static final BigDecimal ANY_VALUE = BigDecimal.ONE;

	@Test
	public void shouldThrowConstraintViolationExceptionWhenFirstNameIsNull() {
		assertThrows(ConstraintViolationException.class,
		             () -> Person.builder()
		                         .firstName(null)
		                         .lastName(LAST_NAME)
		                         .email(EMAIL)
		                         .order(createOrderForTest())
		                         .build());
	}

	@Test
	public void shouldThrowConstraintViolationExceptionWhenFirstNameIsEmpty() {
		assertThrows(ConstraintViolationException.class,
		             () -> Person.builder()
		                         .firstName(" ")
		                         .lastName(LAST_NAME)
		                         .email(EMAIL)
		                         .order(createOrderForTest())
		                         .build());
	}

	@Test
	public void shouldThrowConstraintViolationExceptionWhenLastNameIsNull() {
		assertThrows(ConstraintViolationException.class,
		             () -> Person.builder()
		                         .firstName(FIRST_NAME)
		                         .lastName(null)
		                         .email(EMAIL)
		                         .order(createOrderForTest())
		                         .build());
	}

	@Test
	public void shouldThrowConstraintViolationExceptionWhenLastNameIsEmpty() {
		assertThrows(ConstraintViolationException.class,
		             () -> Person.builder()
		                         .firstName(FIRST_NAME)
		                         .lastName(" ")
		                         .email(EMAIL)
		                         .order(createOrderForTest())
		                         .build());
	}

	@Test
	public void shouldThrowConstraintViolationExceptionWhenEmailIsNotValid() {
		assertThrows(ConstraintViolationException.class,
		             () -> Person.builder()
		                         .firstName(FIRST_NAME)
		                         .lastName(LAST_NAME)
		                         .email(INVALID_EMAIL)
		                         .order(createOrderForTest())
		                         .build());
	}

	@Test
	public void shouldThrowConstraintViolationExceptionWhenEmailIsEmpty() {
		assertThrows(ConstraintViolationException.class,
		             () -> Person.builder()
		                         .firstName(FIRST_NAME)
		                         .lastName(LAST_NAME)
		                         .email(" ")
		                         .order(createOrderForTest())
		                         .build());
	}

	@Test
	public void shouldThrowConstraintViolationExceptionWhenEmailIsNull() {
		assertThrows(ConstraintViolationException.class,
		             () -> Person.builder()
		                         .firstName(FIRST_NAME)
		                         .lastName(LAST_NAME)
		                         .email(null)
		                         .order(createOrderForTest())
		                         .build());
	}

	@Test
	public void shouldThrowConstraintViolationExceptionWhenOrderIsNull() {
		assertThrows(ConstraintViolationException.class,
		             () -> Person.builder()
		             		     .firstName(FIRST_NAME)
		             		     .lastName(LAST_NAME)
		             		     .email(EMAIL)
		             		     .order(null)
		             		     .build());
	}

	@Test
	public void shouldReturnFullNameWithExpectedFormat() {
		var person = Person.builder()
		                   .firstName(FIRST_NAME)
		                   .lastName(LAST_NAME)
		                   .email(EMAIL)
		                   .order(createOrderForTest())
		                   .build();
		
		var returnedFullName = person.getFullName();
		
		assertThat(returnedFullName).isEqualTo(EXPECTED_FULL_NAME);
	}

	private Order createOrderForTest() {
		return new Order(List.of(ANY_VALUE));
	}
}
