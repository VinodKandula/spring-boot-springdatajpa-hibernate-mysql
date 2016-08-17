/**
 * 
 */
package com.teqnihome.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author vinod
 *
 */
@Entity
@Table(name = "team")
public class Team extends AbstractEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull
	@Column(unique=true)
	private String name;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "team", cascade = CascadeType.PERSIST)
	private Set<Student> students;

	public Team() {
	}

	public Team(long id) {
		this.id = id;
	}

	public Team(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}

	@Override
	public String toString() {
		return "Team [name=" + name + ", id=" + id + ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate
				+ "]";
	}
	
}
