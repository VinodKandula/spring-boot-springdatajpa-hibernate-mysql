package com.teqnihome.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Simple Person class without boilerplate.
 *
 * @author Dustin
 */
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class Person {
	@Getter
	@Setter
	private String lastName;

	@Getter
	private String firstName;
}