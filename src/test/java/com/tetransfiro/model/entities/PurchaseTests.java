package com.tetransfiro.model.entities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.tetransfiro.model.AdditionalFactory;
import com.tetransfiro.model.enums.OperationEnum;
import com.tetransfiro.model.enums.SignEnum;
import com.tetransfiro.model.interfaces.IAdditional;

@SpringBootTest
public class PurchaseTests {

	private static final String EMAIL_1 = "email1@test.com";
	private static final String EMAIL_2 = "email2@test.com";
	private static final String FIRST_NAME_1 = "First1";
	private static final String FIRST_NAME_2 = "First2";
	private static final String LAST_NAME_1 = "Last1";
	private static final String LAST_NAME_2 = "Last2";
	private static final List<Order> EXPECTED_ORDERS = List.of(new Order(List.of(BigDecimal.valueOf(2),
	                                                                             BigDecimal.valueOf(40))),
	                                                           new Order(List.of(BigDecimal.valueOf(8))));

	@Test
	public void shouldThrowConstraintViolationExceptionWhenPayingPeopleListIsEmpty() {
		assertThrows(ConstraintViolationException.class,
		             () -> new Purchase(new ArrayList<>(), createAdditionalsListForTest()));
	}

	@Test
	public void shouldThrowConstraintViolationExceptionWhenPayingPeopleListIsNull() {
		assertThrows(ConstraintViolationException.class, () -> new Purchase(null, createAdditionalsListForTest()));
	}

	@Test
	public void shouldThrowConstraintViolationExceptionWhenAdditionalsListIsNull() {
		assertThrows(ConstraintViolationException.class, () -> new Purchase(createPersonListForTest(), null));
	}

	@Test
	public void shouldDetermineTheExpectedOrders() {
		var purchase = new Purchase(createPersonListForTest(), createAdditionalsListForTest());

		var determinedOrders = purchase.getOrders();

		assertThat(determinedOrders, is(EXPECTED_ORDERS));
	}

	@Test
	public void shouldDetermineTheExpectedOrdersTotal() {
		var purchase = new Purchase(createPersonListForTest(), createAdditionalsListForTest());

		var determinedOrdersTotal = purchase.getOrdersTotal();

		assertThat(determinedOrdersTotal).isEqualTo(new BigDecimal("50.00"));
	}

	@Test
	public void shouldDetermineTheExpectedAdditionalsTotal() {
		var purchase = new Purchase(createPersonListForTest(), createAdditionalsListForTest());

		var determinedAdditionalsTotal = purchase.getAdditionalsTotal();

		assertThat(determinedAdditionalsTotal).isEqualTo(new BigDecimal("3.00"));
	}

	private List<IAdditional> createAdditionalsListForTest() {
		return List.of(createAdditionalForTest(BigDecimal.valueOf(8), SignEnum.POSITIVE, OperationEnum.ADD),
		               createAdditionalForTest(BigDecimal.valueOf(10), SignEnum.NEGATIVE, OperationEnum.MULTIPLY));
	}

	private IAdditional createAdditionalForTest(BigDecimal value, SignEnum sign, OperationEnum operation) {
		return AdditionalFactory.getAdditional(value, sign, operation);
	}

	private List<Person> createPersonListForTest() {
		return List.of(Person.builder()
		                     .email(EMAIL_1)
		                     .firstName(FIRST_NAME_1)
		                     .lastName(LAST_NAME_1)
		                     .order(createOrderForTest(BigDecimal.valueOf(2), BigDecimal.valueOf(40)))
		                     .build(),
		               Person.builder()
		                     .email(EMAIL_2)
		                     .firstName(FIRST_NAME_2)
		                     .lastName(LAST_NAME_2)
		                     .order(createOrderForTest(BigDecimal.valueOf(8)))
		                     .build());
	}

	private Order createOrderForTest(BigDecimal... values) {
		return new Order(List.of(values));
	}
}
