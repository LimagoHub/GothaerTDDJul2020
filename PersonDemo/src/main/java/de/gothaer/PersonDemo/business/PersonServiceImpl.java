package de.gothaer.PersonDemo.business;

import java.util.List;

import de.gothaer.PersonDemo.Persistence.PersonRepository;
import de.gothaer.PersonDemo.Persistence.entities.Person;

public class PersonServiceImpl implements PersonService {

	private final PersonRepository personRepository;
	private final List<String> antipathen;
	

	public PersonServiceImpl(final PersonRepository personRepository, final List<String> antipathen) {
		this.personRepository = personRepository;
		this.antipathen = antipathen;
	}



	/**
	 * 1. Parameter darf nicht null sein
	 * 2. Vorname darf nicht null sein
	 * 3. Vorname muss min 2 Zeichen enthalten
	 * 4. Nachname darf nicht null sein
	 * 5. Nachname muss min 2 Zeichen haben
	 * 6. Vorname darf kein Antipath sein...
	 * 7. Person darf erst gespeichert werden wenn Bedingungen erfüllt sind
	 * 8. Egal welcher technischer Fehler auftritt, es soll immer in eine PersonenServiceException gewandelt werden. 
	 */
	@Override
	public void speichern(Person person) throws PersonServiceException {
		
		try {
			speichernImpl(person);
		} catch (RuntimeException e) {
			// ins logfile schreiben
			throw new PersonServiceException("Person konnte nicht gepeichert werden.");
		}
	}



	private void speichernImpl(Person person) throws PersonServiceException {
		checkPerson(person);
		personRepository.save(person);
	}



	private void checkPerson(Person person) throws PersonServiceException {
		plausibilitaetsPruefung(person);
		
		vaidierung(person);
	}



	private void vaidierung(Person person) throws PersonServiceException {
		if(antipathen.contains(person.getVorname()))
			throw new PersonServiceException("Antipath.");
	}



	private void plausibilitaetsPruefung(Person person) throws PersonServiceException {
		if(person == null)
			throw new PersonServiceException("person must not be null");
		
		if(person.getVorname() == null || person.getVorname().length() < 2)
			throw new PersonServiceException("firstname too short.");
		
		if(person.getNachname() == null || person.getNachname().length() < 2)
			throw new PersonServiceException("lastname too short.");
	}

}
