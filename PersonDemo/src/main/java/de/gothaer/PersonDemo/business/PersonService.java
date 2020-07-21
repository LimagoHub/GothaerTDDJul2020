package de.gothaer.PersonDemo.business;

import java.util.List;

import de.gothaer.PersonDemo.Persistence.entities.Person;

public interface PersonService {
	
	public void speichern(Person person) throws PersonServiceException;
	public List<Person> findAllJohns() throws PersonServiceException;

}
