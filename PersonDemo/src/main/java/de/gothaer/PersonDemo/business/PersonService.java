package de.gothaer.PersonDemo.business;

import de.gothaer.PersonDemo.Persistence.entities.Person;

public interface PersonService {
	
	public void speichern(Person person) throws PersonServiceException;
	

}
