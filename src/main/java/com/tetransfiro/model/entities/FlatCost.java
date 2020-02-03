package com.tetransfiro.model.entities;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.tetransfiro.model.ValidatedData;
import com.tetransfiro.model.interfaces.IAdditional;

public class FlatCost extends ValidatedData implements IAdditional {

	@NotNull
	@Positive
	private BigDecimal value;

	public FlatCost(BigDecimal value) {
		this.value = value;
		validate();
	}

	@Override
	public BigDecimal getValueDeterminedBy(BigDecimal ordersTotal) {
		return this.value;
	}
}
