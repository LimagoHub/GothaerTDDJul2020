package de.services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		
		try {
			final Pattern pattern = Pattern.compile("^(M|V)(\\d{10})$");
			
			if(bestellung == null)
				throw new BestellServiceException("Bestellung darf nicht null sein");

			if(creditcard == null)
				throw new BestellServiceException("Kreditkarte darf nicht null sein");

			if(saldo < 0)
				throw new BestellServiceException("Betrag darf nicht negativ sein");
			
			final Matcher matcher = pattern.matcher(creditcard);
			if( ! matcher.matches()) {
				throw new BestellServiceException("Kreditkartennummer ungueltig");
			}
			
			String type = matcher.group(1).equals("M") ? "Master" : "Visa";
			String number = matcher.group(2);
			if(! checkCreditCard.check(type, number, saldo) ) {
				throw new KundeIstPleiteException("Geh heim!");
			}
			
			bestellRepository.save(bestellung);
			
			
		}catch (RemoteException e) {
			throw new BestellServiceException("Kreditkartenprüfung nicht möglich",e);
		}catch (RuntimeException e) {
			throw new BestellServiceException("Bestellung konnte nicht gespeichert werden",e);
		}
	}

}
