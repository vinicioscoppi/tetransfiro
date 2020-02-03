package com.tetransfiro.api.dtos;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class PersonTotalDto {
	private String personFullName;
	private BigDecimal total;
}
