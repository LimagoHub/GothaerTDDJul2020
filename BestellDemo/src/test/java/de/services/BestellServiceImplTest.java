package de.services;

import org.junit.Test;
import static org.mockito.Mockito.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.bestelldemo.persistence.BestellRepository;
import de.bestelldemo.persistence.model.Bestellung;

import static org.junit.Assert.*;
@RunWith(MockitoJUnitRunner.class)
public class BestellServiceImplTest {
	private static final String VALID_VISACARD = "V0123456789";

	private static final Bestellung VALID_ORDER = new Bestellung();

	private static final double VALID_AMOUNT = 10.0;

	private static final String VALID_MASTERCARD = "M0123456789";

	@Mock
	private BestellRepository bestellRepositoryMock;

	@Mock
	private CheckCreditCard checkCreditCardMock;
	
	@InjectMocks
	private BestellServiceImpl objectUnderTest;
	
	/*
	 * 1. Bestellung darf nicht null sein
	 */

	@Test
	public void bestellen_bestellungNull_throwsBestellServiceException() throws  Exception{
		try {
			// Arrange
			
			// Action
			objectUnderTest.bestellen(null, VALID_MASTERCARD, VALID_AMOUNT);
			fail("Upps");
		} catch (BestellServiceException e) {
			// Assertion
			assertEquals("Bestellung darf nicht null sein", e.getMessage());
		} 
	}
	@Test
	public void bestellen_creditcardNull_throwsBestellServiceException() throws  Exception{
		try {
			// Arrange
			
			// Action
			objectUnderTest.bestellen(VALID_ORDER, null, VALID_AMOUNT);
			fail("Upps");
		} catch (BestellServiceException e) {
			// Assertion
			assertEquals("Kreditkarte darf nicht null sein", e.getMessage());
		} 
	}
	@Test
	public void bestellen_AmountNegativ_throwsBestellServiceException() throws  Exception{
		try {
			// Arrange
			
			// Action
			objectUnderTest.bestellen(VALID_ORDER, VALID_MASTERCARD, -1.0);
			fail("Upps");
		} catch (BestellServiceException e) {
			// Assertion
			assertEquals("Betrag darf nicht negativ sein", e.getMessage());
		} 
	}
	
	@Test
	public void bestellen_CreditcardTooShort_throwsBestellServiceException() throws  Exception{
		try {
			// Arrange
			
			// Action
			objectUnderTest.bestellen(VALID_ORDER, "M012345678", VALID_AMOUNT);
			fail("Upps");
		} catch (BestellServiceException e) {
			// Assertion
			assertEquals("Kreditkartennummer ungueltig", e.getMessage());
		} 
	}
	@Test
	public void bestellen_CreditcardTooLong_throwsBestellServiceException() throws  Exception{
		try {
			// Arrange
			
			// Action
			objectUnderTest.bestellen(VALID_ORDER, "M01234567890", VALID_AMOUNT);
			fail("Upps");
		} catch (BestellServiceException e) {
			// Assertion
			assertEquals("Kreditkartennummer ungueltig", e.getMessage());
		} 
	}
	
	@Test
	public void bestellen_CreditcardNeitherMasterNorVisa_throwsBestellServiceException() throws  Exception{
		try {
			// Arrange
			
			// Action
			objectUnderTest.bestellen(VALID_ORDER, "X0123456789", VALID_AMOUNT);
			fail("Upps");
		} catch (BestellServiceException e) {
			// Assertion
			assertEquals("Kreditkartennummer ungueltig", e.getMessage());
		} 
	}
	@Test
	public void bestellen_CreditcardWrongNumber_throwsBestellServiceException() throws  Exception{
		try {
			// Arrange
			
			// Action
			objectUnderTest.bestellen(VALID_ORDER, "M0123X56789", VALID_AMOUNT);
			fail("Upps");
		} catch (BestellServiceException e) {
			// Assertion
			assertEquals("Kreditkartennummer ungueltig", e.getMessage());
		} 
	}
	
	@Test
	public void bestellen__CallCheckCreditcardWithValidArguments_Success() throws  Exception{
		
			// Arrange
			when(checkCreditCardMock.check(anyString(), anyString(), anyDouble())).thenReturn(true);
			// Action
			objectUnderTest.bestellen(VALID_ORDER, VALID_MASTERCARD, VALID_AMOUNT);
			
			// Assertion
			verify(checkCreditCardMock).check("Master", "0123456789", VALID_AMOUNT);
	}
	@Test
	public void bestellen__CallCheckCreditcardWithValidArgumentsWithVisa_Success() throws  Exception{
		
			// Arrange
			when(checkCreditCardMock.check(anyString(), anyString(), anyDouble())).thenReturn(true);
			// Action
			objectUnderTest.bestellen(VALID_ORDER, VALID_VISACARD, VALID_AMOUNT);
			
			// Assertion
			verify(checkCreditCardMock).check("Visa", "0123456789", VALID_AMOUNT);
	}
	@Test(expected = KundeIstPleiteException.class)
	public void bestellen__CustonerInsolvet_throwsKundeIstPleiteExeption() throws  Exception{
		
			// Arrange
			when(checkCreditCardMock.check(anyString(), anyString(), anyDouble())).thenReturn(false);
			// Action
			objectUnderTest.bestellen(VALID_ORDER, VALID_VISACARD, VALID_AMOUNT);
			
			
	}
	@Test
	public void bestellen__HappyDay_OrderIsSavedInPersistence() throws  Exception{
		
			// Arrange
			when(checkCreditCardMock.check(anyString(), anyString(), anyDouble())).thenReturn(true);
			// Action
			objectUnderTest.bestellen(VALID_ORDER, VALID_VISACARD, VALID_AMOUNT);
			
			verify(bestellRepositoryMock).save(VALID_ORDER);
	}
	@Test
	public void bestellen_RemoteExceptionInCreditService_ThrowsBestellServiceException() throws  Exception{
		
			try {
				// Arrange
				when(checkCreditCardMock.check(anyString(), anyString(), anyDouble())).thenThrow(new RemoteException("upps"));
				// Action
				objectUnderTest.bestellen(VALID_ORDER, VALID_VISACARD, VALID_AMOUNT);
				fail("doppelupps");
			}  catch (BestellServiceException e) {
				assertEquals("Kreditkartenprüfung nicht möglich", e.getMessage());
			} 
			
			
	}
	@Test
	public void bestellen_RuntimeExceptionInPersitenceLayer_ThrowsBestellServiceException() throws  Exception{
		
			try {
				// Arrange
				when(checkCreditCardMock.check(anyString(), anyString(), anyDouble())).thenReturn(true);
				doThrow(new RuntimeException()).when(bestellRepositoryMock).save((Bestellung)any());
				// Action
				objectUnderTest.bestellen(VALID_ORDER, VALID_VISACARD, VALID_AMOUNT);
				fail("doppelupps");
			}  catch (BestellServiceException e) {
				assertEquals("Bestellung konnte nicht gespeichert werden", e.getMessage());
			} 
			
			
	}
}
