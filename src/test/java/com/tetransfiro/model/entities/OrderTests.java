package com.tetransfiro.model.entities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrderTests {
	
	private static final BigDecimal EXPECTED_TOTAL = BigDecimal.valueOf(6);

	@Test
	public void shouldThrowConstraintViolationExceptionWhenItemsValueIsEmpty() {
		var emptyList = new ArrayList<BigDecimal>();
		
		assertThrows(ConstraintViolationException.class, () -> new Order(emptyList));
	}

	@Test
	public void shouldThrowConstraintViolationExceptionWhenItemsValueIsNull() {
		assertThrows(ConstraintViolationException.class, () -> new Order(null));
	}
	
	@Test
	public void shouldThrowConstraintViolationExceptionWhenThereIsANegativeValueInTheItemsValueList() {
		var invalidList = createInvalidItemsValueListForTest();
		
		assertThrows(ConstraintViolationException.class, () -> new Order(invalidList));
	}
	
	@Test
	public void shouldDetermineTheExpectedTotal() {
		var itemsValuesList =  createValidItemsValueListForTest();
		var order = new Order(itemsValuesList);
		
		var determinedTotal = order.getOrderTotal();
		
		assertThat(determinedTotal).isEqualTo(EXPECTED_TOTAL);
	}

	private List<BigDecimal> createInvalidItemsValueListForTest() {
		return List.of(BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.valueOf(-1));
	}
	
	private List<BigDecimal> createValidItemsValueListForTest() {
		return List.of(BigDecimal.ONE, BigDecimal.valueOf(2), BigDecimal.valueOf(3));
	}
}
