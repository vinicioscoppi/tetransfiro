package com.tetransfiro.model.entities;

import java.math.BigDecimal;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.tetransfiro.model.ValidatedData;

import lombok.Getter;

@Getter
public class PersonTotal extends ValidatedData {

	@NotBlank
	private String personFullName;

	@Email
	@NotNull
	private String email;

	@NotNull
	@Min(value = 0)
	private BigDecimal total;

	public PersonTotal(String personFullName, String email, BigDecimal total) {
		this.personFullName = personFullName;
		this.email = email;
		this.total = total;
		validate();
	}

}
