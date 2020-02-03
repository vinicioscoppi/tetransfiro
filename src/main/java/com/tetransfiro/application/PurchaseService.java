package com.tetransfiro.application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tetransfiro.amqp.MessagePublisher;
import com.tetransfiro.api.dtos.AdditionalDto;
import com.tetransfiro.api.dtos.OrderDto;
import com.tetransfiro.api.dtos.PersonDto;
import com.tetransfiro.api.dtos.PersonTotalDto;
import com.tetransfiro.api.dtos.PurchaseDto;
import com.tetransfiro.model.AdditionalFactory;
import com.tetransfiro.model.entities.Order;
import com.tetransfiro.model.entities.Person;
import com.tetransfiro.model.entities.PersonTotal;
import com.tetransfiro.model.entities.Purchase;
import com.tetransfiro.model.enums.OperationEnum;
import com.tetransfiro.model.enums.SignEnum;
import com.tetransfiro.model.events.PurchaseTotalDeterminedEvent;
import com.tetransfiro.model.interfaces.IAdditional;

@Service
public class PurchaseService {

	@Autowired
	private MessagePublisher messagePublisher;

	public List<PersonTotalDto> determineTotal(PurchaseDto purchaseDto) throws JsonProcessingException {
		var personTotals = determinePeopleTotal(createPurchase(purchaseDto));

		dispatchPurchaseTotalDeterminedEvent(personTotals);

		return createPersonTotalDtos(personTotals);
	}

	private List<PersonTotalDto> createPersonTotalDtos(List<PersonTotal> personTotals) {
		return personTotals.stream()
		                   .map(personTotal -> new PersonTotalDto(personTotal.getPersonFullName(),
		                                                          personTotal.getTotal()))
		                   .collect(Collectors.toList());
	}

	private List<PersonTotal> determinePeopleTotal(Purchase purchase) {
		return purchase.getPayingPeople()
		               .stream()
		               .map(person -> determinePersonTotal(person, purchase))
		               .collect(Collectors.toList());
	}

	private PersonTotal determinePersonTotal(Person person, Purchase purchase) {
		return new PersonTotal(person.getFullName(),
		                       person.getEmail(),
		                       purchase.determineTotalFor(person.getFullName()));
	}

	private Purchase createPurchase(PurchaseDto dto) {
		return new Purchase(createPayingPeople(dto.getPayingPeople()), createAdditionals(dto.getAdditionals()));
	}

	private List<IAdditional> createAdditionals(Collection<AdditionalDto> dtos) {
		return dtos == null ? new ArrayList<>()
		        : dtos.stream().map(this::createAdditional).collect(Collectors.toList());
	}

	private IAdditional createAdditional(AdditionalDto dto) {
		return AdditionalFactory.getAdditional(dto.getValue(),
		                                       SignEnum.valueOf(dto.getSign()),
		                                       OperationEnum.valueOf(dto.getOperation()));
	}

	private List<Person> createPayingPeople(Collection<PersonDto> dtos) {
		return dtos.stream().map(this::createPerson).collect(Collectors.toList());
	}

	private Person createPerson(PersonDto dto) {
		return Person.builder()
		             .firstName(dto.getFirstName())
		             .lastName(dto.getLastName())
		             .email(dto.getEmail())
		             .order(createOrder(dto.getOrder()))
		             .build();
	}

	private Order createOrder(OrderDto dto) {
		return new Order(dto.getValues());
	}

	private void dispatchPurchaseTotalDeterminedEvent(List<PersonTotal> personTotals) throws JsonProcessingException {
		messagePublisher.send(new ObjectMapper().writeValueAsString(new PurchaseTotalDeterminedEvent(personTotals)));
	}
}
