package com.tetransfiro.model.interfaces;

import java.math.BigDecimal;

public interface IAdditional {
	BigDecimal getValueDeterminedBy(BigDecimal ordersTotal);
}
