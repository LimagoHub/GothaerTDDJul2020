package de.services;

import de.bestelldemo.persistence.BestellRepository;
import de.bestelldemo.persistence.model.Bestellung;

public class BestellServiceImpl implements BestellService {
	
	
	private final BestellRepository bestellRepository;
	private final CheckCreditCard checkCreditCard;
	
	

	public BestellServiceImpl(final BestellRepository bestellRepository, final CheckCreditCard checkCreditCard) {
		this.bestellRepository = bestellRepository;
		this.checkCreditCard = checkCreditCard;
	}



	@Override
	public void bestellen(Bestellung bestellung, String creditcard, double saldo) throws BestellServiceException, KundeIstPleiteException {
		

	}

}
