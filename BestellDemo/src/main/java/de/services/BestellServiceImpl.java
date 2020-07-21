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



	/*
	 * 1. Bestellung darf nicht null sein
	 * 2. Creditcard darf nicht null sein
	 * 3. saldo muss größer gleich null sein
	 * 4. creditcard muss genau 11 Zeichen
	 * 5. creditcard darf nur mit M oder V beginnen
	 * 6. creditcard muss mit genau 10 Ziffern enden.
	 * 7. Technische Fehler Fehler werden in  BestellServiceException gewandelt
	 * 8. Bonitätsprüfung -> Erfolg = Bestellung speichern, KundeIstPleiteException sonst
	 * 
	 * (zusatzaufgabe. Verify in Order)(zusatzaufgabe. Mockmethoden sollen je genau einmal aufgerufen)
	 */
	@Override
	public void bestellen(Bestellung bestellung, String creditcard, double saldo) throws BestellServiceException, KundeIstPleiteException {
		

	}

}
