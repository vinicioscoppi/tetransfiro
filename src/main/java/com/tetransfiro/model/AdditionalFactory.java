package com.tetransfiro.model;

import java.math.BigDecimal;

import com.tetransfiro.model.entities.FlatCost;
import com.tetransfiro.model.entities.FlatDiscount;
import com.tetransfiro.model.entities.PercentCost;
import com.tetransfiro.model.entities.PercentDiscount;
import com.tetransfiro.model.enums.OperationEnum;
import com.tetransfiro.model.enums.SignEnum;
import com.tetransfiro.model.interfaces.IAdditional;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdditionalFactory {

	public static IAdditional getAdditional(@NonNull BigDecimal value,
	                                        @NonNull SignEnum sign,
	                                        @NonNull OperationEnum operation) {

		if (sign == SignEnum.NEGATIVE && operation == OperationEnum.ADD)
			return new FlatDiscount(value);
		if (sign == SignEnum.POSITIVE && operation == OperationEnum.ADD)
			return new FlatCost(value);
		if (sign == SignEnum.NEGATIVE && operation == OperationEnum.MULTIPLY)
			return new PercentDiscount(value);
		if (sign == SignEnum.POSITIVE && operation == OperationEnum.MULTIPLY)
			return new PercentCost(value);

		return null;
	}
}
