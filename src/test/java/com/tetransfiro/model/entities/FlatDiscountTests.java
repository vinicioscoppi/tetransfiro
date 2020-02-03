package com.tetransfiro.model.entities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FlatDiscountTests {

	private static final BigDecimal ANY_NEGATIVE_BIG_DECIMAL_VALUE = BigDecimal.valueOf(-1);
	private static final BigDecimal ANY_POSITIVE_BIG_DECIMAL_VALUE = BigDecimal.TEN;

	@Test
	public void shouldThrowConstraintViolationExceptionWhenValueIsNull() {
		assertThrows(ConstraintViolationException.class, () -> new FlatDiscount(null));
	}

	@Test
	public void shouldThrowConstraintViolationExceptionWhenValueIsNegative() {
		assertThrows(ConstraintViolationException.class, () -> new FlatDiscount(ANY_NEGATIVE_BIG_DECIMAL_VALUE));
	}

	@Test
	public void shouldThrowConstraintViolationExceptionWhenValueIsZero() {
		assertThrows(ConstraintViolationException.class, () -> new FlatDiscount(BigDecimal.ZERO));
	}

	@Test
	public void shouldReturnItsValueNegativeWhenOrdersTotalParameterIsNull() {
		var newFlatDiscount = new FlatDiscount(BigDecimal.ONE);

		var determinedValue = newFlatDiscount.getValueDeterminedBy(null);

		assertThat(determinedValue).isEqualTo(BigDecimal.ONE.negate());
	}

	@Test
	public void shouldReturnItsValueNegativeWhenOrdersTotalParameterIsNegative() {
		var newFlatDiscount = new FlatDiscount(BigDecimal.ONE);

		var determinedValue = newFlatDiscount.getValueDeterminedBy(ANY_NEGATIVE_BIG_DECIMAL_VALUE);

		assertThat(determinedValue).isEqualTo(BigDecimal.ONE.negate());
	}

	@Test
	public void shouldReturnItsValueNegativeWhenOrdersTotalParameterIsPositive() {
		var newFlatDiscount = new FlatDiscount(BigDecimal.ONE);

		var determinedValue = newFlatDiscount.getValueDeterminedBy(ANY_POSITIVE_BIG_DECIMAL_VALUE);

		assertThat(determinedValue).isEqualTo(BigDecimal.ONE.negate());
	}
}
