package de.bestelldemo.persistence;

import de.bestelldemo.persistence.model.Bestellung;

public interface BestellRepository {
	
	void save(Bestellung bestellung);

}
