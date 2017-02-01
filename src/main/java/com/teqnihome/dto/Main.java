package com.teqnihome.dto;

import static java.lang.System.out;

public class Main {
	public static void main(final String[] arguments) {
		// final Person person = new Person();
		final Person person = new Person("Miles", "Linda");
		out.println(person);

		final String sameLastName = "Smith";
		final String sameFirstName = "Sam";
		final Person person1 = new Person(sameLastName, sameFirstName);
		final Person person2 = new Person(sameLastName, sameFirstName);
		if (person1.equals(person2)) {
			out.println("Same person!");
		} else {
			out.println("Different people!");
		}

		final Person accessiblePerson = new Person("Garzminski", "Gary");
		out.println("The last name is " + accessiblePerson.getLastName());
		out.println("The first name is " + accessiblePerson.getFirstName());
		// accessiblePerson.setFirstName("Grady");
		accessiblePerson.setLastName("Garfunkel");
		out.println("The new last name is " + accessiblePerson.getLastName());
	}
}
