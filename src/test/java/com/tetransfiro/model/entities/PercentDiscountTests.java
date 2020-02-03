package com.tetransfiro.model.entities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import javax.validation.ConstraintDeclarationException;
import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;

public class PercentDiscountTests {

	private static final BigDecimal ANY_NEGATIVE_BIG_DECIMAL_VALUE = BigDecimal.valueOf(-1);

	@Test
	public void shouldThrowConstraintViolationExceptionWhenValueIsNull() {
		assertThrows(ConstraintViolationException.class, () -> new PercentDiscount(null));
	}

	@Test
	public void shouldThrowConstraintViolationExceptionWhenValueIsNegative() {
		assertThrows(ConstraintViolationException.class, () -> new PercentDiscount(ANY_NEGATIVE_BIG_DECIMAL_VALUE));
	}
	
	@Test
	public void shouldThrowConstraintViolationExceptionWhenValueIsGreaterThanOneHundred() {
		assertThrows(ConstraintViolationException.class, () -> new PercentDiscount(new BigDecimal("1000")));
	}

	@Test
	public void shouldThrowConstraintViolationExceptionWhenValueIsZero() {
		assertThrows(ConstraintViolationException.class, () -> new PercentDiscount(BigDecimal.ZERO));
	}

	@Test
	public void shouldThrowConstraintDeclarationExceptionWhenOrdersTotalParameterIsNull() {
		var newPercentDiscount = new PercentDiscount(BigDecimal.TEN);

		assertThrows(ConstraintDeclarationException.class, () -> newPercentDiscount.getValueDeterminedBy(null));
	}

	@Test
	public void shouldThrowConstraintDeclarationExceptionWhenOrdersTotalParameterIsNegative() {
		var newPercentDiscount = new PercentDiscount(BigDecimal.TEN);

		assertThrows(ConstraintDeclarationException.class,
		             () -> newPercentDiscount.getValueDeterminedBy(ANY_NEGATIVE_BIG_DECIMAL_VALUE));
	}

	@Test
	public void shouldReturnItsProportionalValueNegativeWhenOrdersTotalParameterIsPositive() {
		var newPercentDiscount = new PercentDiscount(new BigDecimal("10.00"));

		var determinedValue = newPercentDiscount.getValueDeterminedBy(BigDecimal.valueOf(50));

		assertThat(determinedValue).isEqualTo(new BigDecimal("-5.00"));
	}
}
