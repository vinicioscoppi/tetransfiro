package com.tetransfiro.api.dtos;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {

	@NotBlank(message = "First name should not be blank")
	private String firstName;
	
	@NotBlank(message = "Last name should not be blank")
	private String lastName;
	
	@Email(message = "E-mail should be valid")
	private String email;
	
	@Valid
	@NotNull(message = "Order should not be null")
	private OrderDto order;
}
