/**
 * 
 */
package com.teqnihome.service;

import com.teqnihome.model.Student;
import com.teqnihome.service.generic.GenericService;

/**
 * @author vinod
 *
 */
public interface StudentService extends GenericService<Student, Long>{

	public Student findByName(String name); 
		
}
