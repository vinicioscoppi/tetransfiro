package com.tetransfiro.api.dtos;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

	@NotEmpty(message = "Values should not be empty")
	private List<BigDecimal> values;
}
