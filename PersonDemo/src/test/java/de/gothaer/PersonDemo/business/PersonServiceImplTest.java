package de.gothaer.PersonDemo.business;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import de.gothaer.PersonDemo.Persistence.PersonRepository;
import de.gothaer.PersonDemo.Persistence.entities.Person;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class PersonServiceImplTest {
	
	@InjectMocks private PersonServiceImpl objectUnderTest;
	@Mock private PersonRepository personRepositoryMock;
	@Mock private List<String> antipathenMock;
	
//	@Before
//	public void setUp() {
//		personRepositoryMock = Mockito.mock(PersonRepository.class);
//		objectUnderTest = new PersonServiceImpl(personRepositoryMock);
//	}
	
	@Test
	public void speichern_inValidParameterNull_throwsPersonenServiceException()  {
		try {
			objectUnderTest.speichern(null);
			fail("Exception expected!");
		} catch (PersonServiceException e) {
			assertEquals("person must not be null", e.getMessage());
		}
	}

	@Test
	public void speichern_WrongFirstnameNull_throwsPersonenServiceException()  {
		try {
			final Person person = new Person(null, "Doe");
			
			objectUnderTest.speichern(person);
			fail("Exception expected!");
		} catch (PersonServiceException e) {
			assertEquals("firstname too short.", e.getMessage());
		}
	}

	@Test
	public void speichern_WrongFirstnameTooShort_throwsPersonenServiceException()  {
		try {
			final Person person = new Person("J", "Doe");
			
			objectUnderTest.speichern(person);
			fail("Exception expected!");
		} catch (PersonServiceException e) {
			assertEquals("firstname too short.", e.getMessage());
		}
	}
	
	@Test
	public void speichern_WrongLastnameNull_throwsPersonenServiceException()  {
		try {
			final Person person = new Person("John", null);
			
			objectUnderTest.speichern(person);
			fail("Exception expected!");
		} catch (PersonServiceException e) {
			assertEquals("lastname too short.", e.getMessage());
		}
	}

	@Test
	public void speichern_WrongLastnameTooShort_throwsPersonenServiceException()  {
		try {
			final Person person = new Person("John", "D");
			
			objectUnderTest.speichern(person);
			fail("Exception expected!");
		} catch (PersonServiceException e) {
			assertEquals("lastname too short.", e.getMessage());
		}
	}



	@Test
	public void speichern_Antipath_throwsPersonenServiceException()  {
		try {
			// Arrange
			final Person person = new Person("Fritz", "Doe");
			
			when(antipathenMock.contains(anyString())).thenReturn(true);
			
			// Action
			objectUnderTest.speichern(person);
			
			
			fail("Exception expected!"); 
		} catch (PersonServiceException e) {
			
			// Assertion
			assertEquals("Antipath.", e.getMessage());
		}
	}
	
	@Test
	public void speichern_ExceptionInUnderlyingServe_throwsPersonenServiceException()  {
		try {
			// Arrange
			final Person person = new Person("John", "Doe");
			
			doThrow(new ArrayIndexOutOfBoundsException()).when(personRepositoryMock).save((Person) any());
			when(antipathenMock.contains(anyString())).thenReturn(false);
			// Action
			objectUnderTest.speichern(person);
			fail("Exception expected!");
		} catch (PersonServiceException e) {
			assertEquals("Person konnte nicht gepeichert werden.", e.getMessage());
		}
	}
	
	@Test
	public void speichern_HappyDay_PersonSavedInPersistenceLayer() throws Exception{
		
		
		
		final Person person = new Person("John", "Doe");
		when(antipathenMock.contains(anyString())).thenReturn(false);
		objectUnderTest.speichern(person);
		
		verify(personRepositoryMock).save(person);
		assertNotNull(person.getId());
		assertEquals(36, person.getId().length());
	}
	
//	@Test
//	public void speichern_HappyDay_personIDIsSet()  throws Exception{
//		final Person person = new Person("John", "Doe");
//		when(antipathenMock.contains(anyString())).thenReturn(false);
//		objectUnderTest.speichern(person);
//		assertNotNull(person.getId());
//		assertEquals(36, person.getId().length());
//	}
//
	
	@Test
	public void findAllJohns_HappyDay_returnsListOfJohns() throws Exception{
		// Arrange
		List<Person> personen = Arrays.asList(
				new Person("John", "Doe"),
				new Person("John", "Rambo"),
				new Person("John", "Wayne"),
				new Person("John", "Wick"),
				new Person("John", "McClain"),
				new Person("John Boy", "Walton"),
				new Person("Max", "Mustermann"));
		when(personRepositoryMock.findAll()).thenReturn(personen);
		
		// Action
		List<Person> allJohns = objectUnderTest.findAllJohns();
		
		// Assertion
		assertEquals(5, allJohns.size());
		for (Person person : allJohns) {
			assertEquals("John", person.getVorname());
		}
		
	}
	
	@Test(expected = PersonServiceException.class)
	public void findAllJohns_RuntimeExceptionInUnderlyingService_ThrowsPersonenServiceException() throws Exception{
		
		when(personRepositoryMock.findAll()).thenThrow(new RuntimeException());
		
		// Action
		objectUnderTest.findAllJohns();
		
		
		
	}
	
}
 