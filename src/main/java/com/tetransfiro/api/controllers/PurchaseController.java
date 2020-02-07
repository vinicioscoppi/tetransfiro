package com.tetransfiro.api.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tetransfiro.api.dtos.PersonTotalDto;
import com.tetransfiro.api.dtos.PurchaseDto;
import com.tetransfiro.application.PurchaseService;
import com.tetransfiro.model.exceptions.TTConstraintViolationException;
import com.tetransfiro.utils.IntegrityValidator;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

	@Autowired
	private PurchaseService purchaseService;

	@ResponseBody
	@PostMapping("/determine")
	public Collection<PersonTotalDto> determineTotalPerPerson(@RequestBody PurchaseDto purchaseDto)
	        throws JsonProcessingException {

		IntegrityValidator.validate(purchaseDto)
		                  .ifPresent(violations -> { throw new TTConstraintViolationException(violations); });

		return purchaseService.determineTotal(purchaseDto);
	}
}
