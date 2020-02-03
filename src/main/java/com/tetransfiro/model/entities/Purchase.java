package com.tetransfiro.model.entities;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.tetransfiro.model.ValidatedData;
import com.tetransfiro.model.interfaces.IAdditional;

import lombok.Getter;

@Getter
public class Purchase extends ValidatedData {

	private static final int CURRENCY_RELEVANT_SCALE = 2;

	@Valid
	@NotEmpty
	private List<Person> payingPeople;

	@Valid
	@NotNull
	private List<IAdditional> additionals;

	private List<Order> orders;
	private BigDecimal ordersTotal;
	private BigDecimal additionalsTotal;

	public Purchase(List<Person> payingPeople, List<IAdditional> additionals) {
		this.payingPeople = payingPeople;
		this.additionals = additionals;
		validate();
		
		this.orders = getPayingPeopleOrders();
		this.ordersTotal = sumOrdersValues();
		this.additionalsTotal = sumAdditionalsValues();
	}

	public BigDecimal determineTotalFor(String personName) {
		return findOrderByPersonFullName(personName).getOrderTotal()
		                                            .add(determineProportionalAdditionalsFor(personName))
		                                            .setScale(CURRENCY_RELEVANT_SCALE);
	}

	private List<Order> getPayingPeopleOrders() {
		return this.payingPeople.stream().map(Person::getOrder).collect(Collectors.toList());
	}

	private BigDecimal determineProportionalAdditionalsFor(String personName) {
		return this.additionalsTotal.multiply(determineProportionFor(personName));
	}

	private BigDecimal determineProportionFor(String personName) {
		return findOrderByPersonFullName(personName).getOrderTotal().divide(ordersTotal);
	}

	private Order findOrderByPersonFullName(String personName) {
		return this.payingPeople.stream()
		                        .filter(person -> person.getFullName().equals(personName))
		                        .findFirst()
		                        .orElseThrow(RuntimeException::new)
		                        .getOrder();
	}

	private BigDecimal sumOrdersValues() {
		return this.orders.stream()
		                  .map(Order::getOrderTotal)
		                  .reduce(BigDecimal.ZERO, BigDecimal::add)
		                  .setScale(CURRENCY_RELEVANT_SCALE);
	}

	private BigDecimal sumAdditionalsValues() {
		return this.additionals.stream()
		                       .map(additional -> additional.getValueDeterminedBy(ordersTotal))
		                       .reduce(BigDecimal.ZERO, BigDecimal::add)
		                       .setScale(CURRENCY_RELEVANT_SCALE);
	}
}
