package com.tetransfiro.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tetransfiro.api.dtos.AdditionalDto;
import com.tetransfiro.api.dtos.PersonDto;
import com.tetransfiro.api.dtos.PurchaseDto;
import com.tetransfiro.application.PurchaseService;

@SpringBootTest
public class PurchaseControllerTests {

	private static final String PATH = "/purchase/determine";

	private ObjectMapper objectMapper = new ObjectMapper();
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@MockBean
	private PurchaseService purchaseService;

	@BeforeEach
	void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void shouldRespondWithCode400WhenPurchaseDtoIsNotValid() throws Exception {
		var invalidPurchaseDto = createPurchaseDtoJsonForTest(new ArrayList<>(), new ArrayList<>());

		mockMvc.perform(request(HttpMethod.POST, PATH).contentType(MediaType.APPLICATION_JSON_VALUE)
		                                              .content(invalidPurchaseDto))
		       .andExpect(status().isBadRequest());
	}

	@Test
	public void shouldRespondWithCode400WhenPurchaseDtoIsNull() throws Exception {
		mockMvc.perform(request(HttpMethod.POST, PATH).contentType(MediaType.APPLICATION_JSON_VALUE).content(""))
		       .andExpect(status().isBadRequest());
	}

	private String createPurchaseDtoJsonForTest(List<PersonDto> personDtoList, List<AdditionalDto> additionalDtoList)
	        throws JsonProcessingException {
		return objectMapper.writeValueAsString(new PurchaseDto(personDtoList, additionalDtoList));
	}
}
