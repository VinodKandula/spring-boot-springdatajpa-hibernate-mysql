/**
 * 
 */
package com.teqnihome.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * @author vinod
 *
 */
@MappedSuperclass
public abstract class AbstractEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	@Column(insertable = true, updatable = false)
	@CreatedDate
	LocalDateTime createdDate = LocalDateTime.now();

	@Column(insertable = true, updatable = true)
	@LastModifiedDate
	LocalDateTime modifiedDate = LocalDateTime.now();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "AbstractEntity [id=" + id + ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate + "]";
	}

}
