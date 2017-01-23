package com.example.item;

public class Coffee {
	
	private int coffeeNumber;
	private static Coffee referenceObject = new Coffee(); // Common object that will be used by all Coffee consumer
														  // threads to register them.
	
	public Coffee() {

	}

	public static Coffee getReferenceObject() {
		return referenceObject;
	}

	public Coffee(int coffeeNumber) {
		this.coffeeNumber = coffeeNumber;
	}

	@Override
	public String toString() {
		return "Coffee [coffeeNumber=" + coffeeNumber + "]";
	}
}
