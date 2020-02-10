package com.tetransfiro.application;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.anyString;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tetransfiro.amqp.MessagePublisher;
import com.tetransfiro.api.dtos.AdditionalDto;
import com.tetransfiro.api.dtos.OrderDto;
import com.tetransfiro.api.dtos.PersonDto;
import com.tetransfiro.api.dtos.PersonTotalDto;
import com.tetransfiro.api.dtos.PurchaseDto;

@SpringBootTest
public class PurchaseServiceTests {

	@MockBean
	private MessagePublisher messagePublisher;

	@Autowired
	private PurchaseService purchaseService;

	private static final String FULL_NAME_1 = "First1 Last1";
	private static final String FULL_NAME_2 = "First2 Last2";
	private static final String FIRST_NAME_1 = "First1";
	private static final String FIRST_NAME_2 = "First2";
	private static final String LAST_NAME_1 = "Last1";
	private static final String LAST_NAME_2 = "Last2";
	private static final String EMAIL_1 = "email1@test.com";
	private static final String EMAIL_2 = "email2@test.com";
	private static final String MULTIPLY = "MULTIPLY";
	private static final String ADD = "ADD";
	private static final String NEGATIVE = "NEGATIVE";
	private static final String POSITIVE = "POSITIVE";

	@Test
	public void shouldDetermineTheExpectedPeopleTotal() throws JsonProcessingException {
		var purchaseDto = createPurchaseDtoForTest();
		var expectedPersonTotalDtoList = createPersonTotalDtoListForTest();

		var determinedPersonTotalDtoList = purchaseService.determineTotal(purchaseDto);

		assertThat(determinedPersonTotalDtoList, is(expectedPersonTotalDtoList));
	}
	
	@Test
	public void shouldCallMessagePublisherExactlyOnceWhenProcessingIsOver() throws JsonProcessingException {
		var purchaseDto = createPurchaseDtoForTest();

		purchaseService.determineTotal(purchaseDto);

		verify(messagePublisher, times(1)).send(anyString());
	}

	private PurchaseDto createPurchaseDtoForTest() {
		return new PurchaseDto(createPersonDtoListForTest(), createAdditionalDtoListForTest());
	}

	private List<PersonTotalDto> createPersonTotalDtoListForTest() {
		return List.of(new PersonTotalDto(FULL_NAME_1, new BigDecimal("42.00")),
		               new PersonTotalDto(FULL_NAME_2, new BigDecimal("8.00")));
	}

	private List<PersonDto> createPersonDtoListForTest() {
		return List.of(new PersonDto(FIRST_NAME_1,
		                             LAST_NAME_1,
		                             EMAIL_1,
		                             createOrderDtoForTest(BigDecimal.valueOf(40), BigDecimal.valueOf(2))),
		               new PersonDto(FIRST_NAME_2, LAST_NAME_2, EMAIL_2, createOrderDtoForTest(BigDecimal.valueOf(8))));
	}

	private List<AdditionalDto> createAdditionalDtoListForTest() {
		return List.of(new AdditionalDto(MULTIPLY, NEGATIVE, BigDecimal.valueOf(10)),
		               new AdditionalDto(ADD, POSITIVE, BigDecimal.valueOf(8)));
	}

	private OrderDto createOrderDtoForTest(BigDecimal... values) {
		return new OrderDto(List.of(values));
	}

}
