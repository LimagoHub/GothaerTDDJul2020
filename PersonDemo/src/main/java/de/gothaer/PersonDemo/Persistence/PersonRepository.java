package de.gothaer.PersonDemo.Persistence;

import java.util.List;

import de.gothaer.PersonDemo.Persistence.entities.Person;

public interface PersonRepository {
	
	public void save(Person person); // C von Crud
	public void update(Person person); // U von Crud
	public void delete(Person person); // d von Crud

	public Person findByPrimaryKey(String key);
	public List<Person> findAll();
	

}
