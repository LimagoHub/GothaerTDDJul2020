package de.services;

import de.bestelldemo.persistence.model.Bestellung;

public interface BestellService {
	
	void bestellen(Bestellung bestellung, String creditcard /*M0123456789*/, double saldo) throws BestellServiceException, KundeIstPleiteException;

}
