package com.tetransfiro.model.events;

import java.util.Collection;

import com.tetransfiro.model.entities.PersonTotal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@NoArgsConstructor
public class PurchaseTotalDeterminedEvent {

	private String name = "PurchaseTotalDeterminedEvent";

	@NonNull
	private Collection<PersonTotal> personTotals;

	public PurchaseTotalDeterminedEvent(Collection<PersonTotal> personTotals) {
		this.personTotals = personTotals;
	}
}
