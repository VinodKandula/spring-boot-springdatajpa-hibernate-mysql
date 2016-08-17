/**
 * 
 */
package com.teqnihome.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author vinod
 *
 */
@Entity
@Table(name = "student")
public class Student extends AbstractEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull
	private String name;

	@ManyToOne(optional = true, fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private Team team;

	public Student() {
	}

	public Student(long id) {
		this.id = id;
	}
	
	public Student(String name, Team team) {
		this.name = name;
		this.team = team;
	}
	
	public Student(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	@Override
	public String toString() {
		return "Student [name=" + name + ", id=" + id + ", createdDate=" + createdDate + ", modifiedDate="
				+ modifiedDate + "]";
	}

}
