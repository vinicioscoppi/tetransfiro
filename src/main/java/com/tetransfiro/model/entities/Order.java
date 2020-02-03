package com.tetransfiro.model.entities;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import com.tetransfiro.model.ValidatedData;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class Order extends ValidatedData {

	@NotEmpty
	private List<@Min(value = 0) BigDecimal> itemsValues;

	public Order(List<BigDecimal> itemsValues) {
		this.itemsValues = itemsValues;
		validate();
	}

	public BigDecimal getOrderTotal() {
		return this.itemsValues.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}
