package com.tetransfiro.api.dtos;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDto {

	@Valid
	@NotEmpty
	private List<PersonDto> payingPeople;

	@Valid
	private List<AdditionalDto> additionals;
}
