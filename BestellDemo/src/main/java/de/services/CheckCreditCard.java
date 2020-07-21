package de.services;

public interface CheckCreditCard {
	
	boolean check(String type /* Master oder Visa */, String cardnumber /* genau 10 Ziffern */, double saldo) throws RemoteException;

}
