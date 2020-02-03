package com.tetransfiro.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.tetransfiro.model.AdditionalFactory;
import com.tetransfiro.model.entities.FlatCost;
import com.tetransfiro.model.entities.FlatDiscount;
import com.tetransfiro.model.entities.PercentCost;
import com.tetransfiro.model.entities.PercentDiscount;
import com.tetransfiro.model.enums.OperationEnum;
import com.tetransfiro.model.enums.SignEnum;

@SpringBootTest
public class AdditionalFactoryTests {

	private static final BigDecimal ANY_BIG_DECIMAL_VALUE = BigDecimal.ONE;
	private static final OperationEnum ANY_OPERATION = OperationEnum.ADD;
	private static final SignEnum ANY_SIGN = SignEnum.NEGATIVE;

	@Test
	public void shouldReturnAnInstanceOfFlatCostWhenOperationIsAddAndSignIsPositive() {

		var returnedObject = AdditionalFactory.getAdditional(ANY_BIG_DECIMAL_VALUE,
		                                                     SignEnum.POSITIVE,
		                                                     OperationEnum.ADD);

		assertThat(returnedObject).isInstanceOf(FlatCost.class);
	}

	@Test
	public void shouldReturnAnInstanceOfFlatDiscountWhenOperationIsAddAndSignIsNegative() {

		var returnedObject = AdditionalFactory.getAdditional(ANY_BIG_DECIMAL_VALUE,
		                                                     SignEnum.NEGATIVE,
		                                                     OperationEnum.ADD);

		assertThat(returnedObject).isInstanceOf(FlatDiscount.class);
	}

	@Test
	public void shouldReturnAnInstanceOfPercentCostWhenOperationIsMultiplyAndSignIsPositive() {

		var returnedObject = AdditionalFactory.getAdditional(ANY_BIG_DECIMAL_VALUE,
		                                                     SignEnum.POSITIVE,
		                                                     OperationEnum.MULTIPLY);

		assertThat(returnedObject).isInstanceOf(PercentCost.class);
	}

	@Test
	public void shouldReturnAnInstanceOfPercentDiscountWhenOperationIsMutiplyAndSignIsNegative() {

		var returnedObject = AdditionalFactory.getAdditional(ANY_BIG_DECIMAL_VALUE,
		                                                     SignEnum.NEGATIVE,
		                                                     OperationEnum.MULTIPLY);

		assertThat(returnedObject).isInstanceOf(PercentDiscount.class);
	}

	@Test
	public void shouldReturnNullPointerExceptionWhenValueParameterIsNull() {
		assertThrows(NullPointerException.class, () -> AdditionalFactory.getAdditional(null, ANY_SIGN, ANY_OPERATION));
	}

	@Test
	public void shouldReturnNullPointerExceptionWhenSignParameterIsNull() {
		assertThrows(NullPointerException.class,
		             () -> AdditionalFactory.getAdditional(ANY_BIG_DECIMAL_VALUE, null, ANY_OPERATION));
	}

	@Test
	public void shouldReturnNullPointerExceptionWhenOperationParameterIsNull() {
		assertThrows(NullPointerException.class,
		             () -> AdditionalFactory.getAdditional(ANY_BIG_DECIMAL_VALUE, ANY_SIGN, null));
	}
}
