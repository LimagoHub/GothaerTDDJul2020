package de.gothaer.PersonDemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.*;
@RunWith(MockitoJUnitRunner.class)
public class MyServiceImplTest {
	@Mock
	private MyDependency myDependencyMock;
	@InjectMocks
	private MyServiceImpl objectUnderTest;
	
	
	@Test
	public void foo_test() {
				
//		// Kein ReturnValue (Record-Modus) bei Void Methoden
//		doThrow(new IllegalArgumentException("Upps")).when(myDependencyMock).eins(anyString());
//		doThrow(new ArithmeticException("Upps")).when(myDependencyMock).eins("B");
//		doThrow(new ArrayIndexOutOfBoundsException("Upps")).when(myDependencyMock).eins("A");
		
//		doNothing().when(myDependencyMock).eins(anyString());
		// Replay
		
		
//		objectUnderTest.foo();
//		verify(myDependencyMock, times(2)).eins(anyString());
	
		
		// Arrange
		// Recordmodus nicht Void Methoden
//		when(myDependencyMock.zwei()).thenReturn(42);
//		when(myDependencyMock.zwei()).thenThrow(new RuntimeException());
//		objectUnderTest.foo();
//		verify(myDependencyMock, times(1)).zwei();
		
		when(myDependencyMock.drei(anyString())).thenReturn(50);
		objectUnderTest.foo();
		verify(myDependencyMock).drei("Abdu");
		
	}

}
