package de.collections;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class StapelTest {
	
	
	private Stapel objectUnderTest;
	
	@Before
	public void setUp() {
		objectUnderTest = new Stapel();
	}

	@Test
	public void isEmpty_emptyStack_returnsTrue() {
		
		// Assertion
		assertTrue(objectUnderTest.isEmpty());
		
	}

	@Test
	public void isEmpty_notEmptyStack_returnsFalse() throws Exception{
		
		// Arrange
		objectUnderTest.push(new Object());
		
		// Action + Assertion
		assertFalse(objectUnderTest.isEmpty());
		
	}
	
	@Test
	public void push_notEmptyStack_noExceptionIsThrown() throws Exception{
		final Object object = new Object();
		// Action
		objectUnderTest.push(object);
		
		// Assertion
		assertFalse(objectUnderTest.isEmpty());
		assertSame(object, objectUnderTest.pop());
		
	}
	
	@Test
	public void push_fillUpToLimit_noExceptionIsThrown() throws Exception{
		
		fillUpToLimit();
		
		// Assertion
		// Ok
	}
	

	@Test(expected = StapelException.class)
	public void push_Overflow_throwsStapelException() throws Exception{
		
		fillUpToLimit();
		objectUnderTest.push(new Object());
		
		
	}
	

	@Test
	public void push_OverflowVariante2_throwsStapelException() throws Exception{
		
		try {
			fillUpToLimit();
			objectUnderTest.push(new Object());
			fail("Upps");
		} catch (StapelException e) {
			assertEquals("Overflow", e.getMessage());
		}
		
		
	}

	private void fillUpToLimit() throws StapelException {
		for (int i = 0; i < 10; i++) {
			// Action
			objectUnderTest.push(new Object());
		}
	}
	

}
