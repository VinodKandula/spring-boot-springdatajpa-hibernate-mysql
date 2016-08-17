/**
 * 
 */
package com.teqnihome.service;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.teqnihome.model.Student;
import com.teqnihome.model.Team;
import com.teqnihome.repository.StudentRepository;
import com.teqnihome.repository.TeamRepository;
import com.teqnihome.service.generic.GenericServiceImpl;

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

		return studentRepository.save(updateStudent);
	}

	@Override
	public Student findByName(String name) {
		return studentRepository.findByName(name);
	}

}
