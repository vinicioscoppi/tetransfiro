package com.tetransfiro.model.entities;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.util.StringUtils;

import com.tetransfiro.model.ValidatedData;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Person extends ValidatedData {

	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	@Email
	@NotNull
	private String email;

	@Valid
	@NotNull
	private Order order;
	
	@Builder
	public Person(String firstName,
	              String lastName,
	              String email,
	              Order order) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.order = order;
		validate();
	}

	public String getFullName() {
		return String.format("%s %s", StringUtils.capitalize(this.firstName), StringUtils.capitalize(this.lastName));
	}
}
