package de.gothaer.PersonDemo.business;

import de.gothaer.PersonDemo.Persistence.PersonRepository;
import de.gothaer.PersonDemo.Persistence.entities.Person;

public class PersonServiceImpl implements PersonService {

	private final PersonRepository personRepository;
	
	

	public PersonServiceImpl(final PersonRepository personRepository) {
		this.personRepository = personRepository;
	}



	/**
	 * 1. Parameter darf nicht null sein
	 * 2. Vorname darf nicht null sein
	 * 3. Vorname muss min 2 Zeichen enthalten
	 * 4. Nachname darf nicht null sein
	 * 5. Nachname muss min 2 Zeichen haben
	 * 6. Vorname darf nicht Attila sein
	 * 7. Person darf erst gespeichert werden wenn Bedingungen erfüllt sind
	 * 8. Egal welcher technischer Fehler auftritt, es soll immer in eine PersonenServiceException gewandelt werden. 
	 */
	@Override
	public void speichern(Person person) throws PersonServiceException {
		

	}

}
