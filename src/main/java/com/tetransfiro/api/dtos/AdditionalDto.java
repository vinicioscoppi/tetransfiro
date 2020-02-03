package com.tetransfiro.api.dtos;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdditionalDto {

	@NotBlank(message = "Operation should not be empty")
	private String operation;

	@NotEmpty(message = "Sign should not be empty")
	private String sign;

	@Min(value = 0, message = "Value should be greater than zero")
	private BigDecimal value;
}
