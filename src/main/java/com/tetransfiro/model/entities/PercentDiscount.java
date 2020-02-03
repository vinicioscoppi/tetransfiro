package com.tetransfiro.model.entities;

import java.math.BigDecimal;

import javax.validation.ConstraintDeclarationException;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.tetransfiro.model.ValidatedData;
import com.tetransfiro.model.interfaces.IAdditional;

public class PercentDiscount extends ValidatedData implements IAdditional {

	@NotNull
	@Positive
	@Max(value = 100)
	private BigDecimal value;

	public PercentDiscount(BigDecimal value) {
		this.value = value;
		validate();
	}

	@Override
	public BigDecimal getValueDeterminedBy(BigDecimal ordersTotal) {
		if (ordersTotal == null || ordersTotal.compareTo(BigDecimal.ZERO) <= 0)
			throw new ConstraintDeclarationException("Parameter ordersTotal is not valid");

		return this.value.divide(new BigDecimal(100)).multiply(ordersTotal).multiply(new BigDecimal(-1));
	}
}
