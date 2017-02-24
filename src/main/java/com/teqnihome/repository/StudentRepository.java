/**
 * 
 */
package com.teqnihome.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.mysema.query.types.path.StringPath;
import com.teqnihome.model.QStudent;
import com.teqnihome.model.Student;

/**
 * @author vinod
 *
 */
@Transactional
public interface StudentRepository extends JpaRepository<Student, Long>, QueryDslPredicateExecutor<Student>, QuerydslBinderCustomizer<QStudent> {
	
	public Student findByName(String name);
	
	@Override
	  default public void customize(QuerydslBindings bindings, QStudent qStudent) {

	    bindings.bind(qStudent.lastName).first((path, value) -> path.contains(value));    
	    bindings.bind(String.class)
	      .first((StringPath path, String value) -> path.containsIgnoreCase(value)); 
	    //bindings.excluding(qStudent.password);                                           
	  }
	
}
