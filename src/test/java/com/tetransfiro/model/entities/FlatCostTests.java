package com.tetransfiro.model.entities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FlatCostTests {

	private static final BigDecimal ANY_NEGATIVE_BIG_DECIMAL_VALUE = BigDecimal.valueOf(-1);
	private static final BigDecimal ANY_POSITIVE_BIG_DECIMAL_VALUE = BigDecimal.TEN;

	@Test
	public void shouldThrowConstraintViolationExceptionWhenValueIsNull() {
		assertThrows(ConstraintViolationException.class, () -> new FlatCost(null));
	}

	@Test
	public void shouldThrowConstraintViolationExceptionWhenValueIsNegative() {
		assertThrows(ConstraintViolationException.class, () -> new FlatCost(ANY_NEGATIVE_BIG_DECIMAL_VALUE));
	}

	@Test
	public void shouldThrowConstraintViolationExceptionWhenValueIsZero() {
		assertThrows(ConstraintViolationException.class, () -> new FlatCost(BigDecimal.ZERO));
	}

	@Test
	public void shouldReturnItsValueWhenOrdersTotalParameterIsNull() {
		var newFlatCost = new FlatCost(BigDecimal.ONE);

		var determinedValue = newFlatCost.getValueDeterminedBy(null);

		assertThat(determinedValue).isEqualTo(BigDecimal.ONE);
	}

	@Test
	public void shouldReturnItsValueWhenOrdersTotalParameterIsNegative() {
		var newFlatCost = new FlatCost(BigDecimal.ONE);

		var determinedValue = newFlatCost.getValueDeterminedBy(ANY_NEGATIVE_BIG_DECIMAL_VALUE);

		assertThat(determinedValue).isEqualTo(BigDecimal.ONE);
	}

	@Test
	public void shouldReturnItsValueWhenOrdersTotalParameterIsPositive() {
		var newFlatCost = new FlatCost(BigDecimal.ONE);

		var determinedValue = newFlatCost.getValueDeterminedBy(ANY_POSITIVE_BIG_DECIMAL_VALUE);

		assertThat(determinedValue).isEqualTo(BigDecimal.ONE);
	}
}
