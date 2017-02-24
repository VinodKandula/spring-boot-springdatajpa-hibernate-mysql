/**
 * 
 */
package com.teqnihome.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.mysema.query.types.Path;
import com.mysema.query.types.Predicate;
import com.teqnihome.model.QStudent;
import com.teqnihome.model.Student;
import com.teqnihome.model.Team;
import com.teqnihome.repository.StudentRepository;
import com.teqnihome.repository.TeamRepository;
import com.teqnihome.service.generic.GenericServiceImpl;
import com.teqnihome.service.qdsl.QueryDSLPredicateBuilder;

/**
 * @author vinod
 *
 */
@Service
@Transactional
public class StudentServiceImpl extends GenericServiceImpl<Student, Long> implements StudentService {

	@Resource
	private StudentRepository studentRepository;

	@Resource
	private TeamRepository teamRepository;

	@Override
	public List<Student> findAll() {
		return studentRepository.findAll();
	}
	
	@Override
	@Transactional(rollbackFor = EntityNotFoundException.class)
	public Student update(Student student) throws EntityNotFoundException {
		Student updateStudent = studentRepository.findOne(student.getId());
		if (updateStudent == null)
			throw new EntityNotFoundException();

		if (!StringUtils.isEmpty(student.getName())) {
			updateStudent.setName(student.getName());
		}

		if (!StringUtils.isEmpty(student.getTeam().getName())) {
			// find Team by Name
			Team team = teamRepository.findByName(student.getTeam().getName());

			if (team == null)
				throw new EntityNotFoundException();

			updateStudent.setTeam(team);

		}
		
		findByNameLastName("Vinod", "Reddy");

		return studentRepository.save(updateStudent);
	}

	@Override
	public Student findByName(String name) {
		return studentRepository.findByName(name);
	}

	@Override
	public Student findByNameLastName(String name, String lastName) {
		QStudent qStudent = QStudent.student;
		Predicate predicate = qStudent.name.eq(name).and(qStudent.lastName.eq(lastName));
		
		Iterable<Student> itr = studentRepository.findAll(predicate);
		
		for(Iterator<Student> studentIterator = itr.iterator(); studentIterator.hasNext(); ) {
			Student student = studentIterator.next();
			System.out.println("########## Student Details : "+student);
		}
		
		queryDSL(null);
		
		return null;
	}

	@Override
	public Student queryDSL(String expression) {
		
		QStudent qStudent = QStudent.student;
		Path<?>[] paths = new Path<?>[] {qStudent.id, qStudent.name, qStudent.lastName, qStudent.team};
		Map<String, Path<?>> pathMap = new HashMap<>();
		pathMap.put(qStudent.id.toString(), qStudent.id);
		pathMap.put(qStudent.name.toString(), qStudent.name);
		pathMap.put(qStudent.lastName.toString(), qStudent.lastName);
		
		Predicate filter = QueryDSLPredicateBuilder.getPredicate(QStudent.class, "student", expression);
		
		Iterable<Student> itr = studentRepository.findAll(filter);
		
		for(Iterator<Student> studentIterator = itr.iterator(); studentIterator.hasNext(); ) {
			Student student = studentIterator.next();
			System.out.println("########## Student Details : "+student);
		}
		
		return null;
	}
	
	static {
		QStudent qStudent = QStudent.student;
		Path<?>[] paths = new Path<?>[] {qStudent.id, qStudent.name, qStudent.lastName, qStudent.team};
		Map<String, Path<?>> pathMap = new HashMap<>();
		
	}

}
