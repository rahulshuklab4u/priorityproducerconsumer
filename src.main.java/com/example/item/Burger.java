package com.example.item;

public class Burger {

	private int burgerNumber;
	
	private static Burger referenceObject = new Burger();// Common object that will be used by all Burger consumer
														 // threads to register them.
	
	public Burger() {

	}

	public static Burger getReferenceObject() {
		return referenceObject;
	}

	public Burger(int burgerNumber) {
		this.burgerNumber = burgerNumber;
	}

	@Override
	public String toString() {
		return "Burger [burgerNumber=" + burgerNumber + "]";
	}
}
