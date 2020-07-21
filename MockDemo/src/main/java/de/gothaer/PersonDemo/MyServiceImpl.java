package de.gothaer.PersonDemo;

public class MyServiceImpl implements MyService {

	private final MyDependency myDependency;
	
	

	public MyServiceImpl(final MyDependency myDependency) {
		this.myDependency = myDependency;
	}



	@Override
	public void foo() {
		// Anderer Kram
		// eins
//		myDependency.eins("Hallo Welt");
//		myDependency.eins("Hello World");
		
//		int zwei = myDependency.zwei();
//		
//		System.out.println(zwei + 5);
		
		int drei = myDependency.drei("Abdu");
		System.out.println(drei);
		
	}

}
