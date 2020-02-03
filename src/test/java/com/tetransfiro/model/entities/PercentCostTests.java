package com.tetransfiro.model.entities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import javax.validation.ConstraintDeclarationException;
import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;

public class PercentCostTests {

	private static final BigDecimal ANY_NEGATIVE_BIG_DECIMAL_VALUE = BigDecimal.valueOf(-1);

	@Test
	public void shouldThrowConstraintViolationExceptionWhenValueIsNull() {
		assertThrows(ConstraintViolationException.class, () -> new PercentCost(null));
	}

	@Test
	public void shouldThrowConstraintViolationExceptionWhenValueIsNegative() {
		assertThrows(ConstraintViolationException.class, () -> new PercentCost(ANY_NEGATIVE_BIG_DECIMAL_VALUE));
	}
	
	@Test
	public void shouldThrowConstraintViolationExceptionWhenValueIsGreaterThanOneHundred() {
		assertThrows(ConstraintViolationException.class, () -> new PercentCost(new BigDecimal("1000")));
	}

	@Test
	public void shouldThrowConstraintViolationExceptionWhenValueIsZero() {
		assertThrows(ConstraintViolationException.class, () -> new PercentCost(BigDecimal.ZERO));
	}

	@Test
	public void shouldThrowConstraintDeclarationExceptionWhenOrdersTotalParameterIsNull() {
		var newPercentCost = new PercentCost(BigDecimal.TEN);

		assertThrows(ConstraintDeclarationException.class, () -> newPercentCost.getValueDeterminedBy(null));
	}

	@Test
	public void shouldThrowConstraintDeclarationExceptionWhenOrdersTotalParameterIsNegative() {
		var newPercentCost = new PercentCost(BigDecimal.TEN);

		assertThrows(ConstraintDeclarationException.class,
		             () -> newPercentCost.getValueDeterminedBy(ANY_NEGATIVE_BIG_DECIMAL_VALUE));
	}

	@Test
	public void shouldReturnItsProportionalValueWhenOrdersTotalParameterIsPositive() {
		var newPercentCost = new PercentCost(new BigDecimal("10.00"));

		var determinedValue = newPercentCost.getValueDeterminedBy(BigDecimal.valueOf(100));

		assertThat(determinedValue).isEqualTo(new BigDecimal("10.00"));
	}
}
