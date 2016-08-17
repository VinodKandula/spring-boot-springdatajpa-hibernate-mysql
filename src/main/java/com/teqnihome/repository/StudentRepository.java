/**
 * 
 */
package com.teqnihome.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teqnihome.model.Student;

/**
 * @author vinod
 *
 */
@Transactional
public interface StudentRepository extends JpaRepository<Student, Long>{
	
	public Student findByName(String name);
	
}
