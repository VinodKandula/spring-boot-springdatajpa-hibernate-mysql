package com.teqnihome.controller;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysema.query.types.Predicate;
import com.teqnihome.model.Student;
import com.teqnihome.model.Team;
import com.teqnihome.repository.StudentRepository;
import com.teqnihome.service.StudentService;
import com.teqnihome.service.TeamService;

/**
 *
 * @author Vinod
 */
@Controller
@RequestMapping(value = "/student")
public class StudentController {

	@Autowired
	private StudentService studentService;
	
	@Autowired
	private TeamService teamService;

	@RequestMapping("/create")
	@ResponseBody
	public String create(String name, String teamName) {
		Student student = null;
		try {
			student = new Student(name);
			
			if (!StringUtils.isEmpty(teamName)) {
				Team team = teamService.findByName(teamName);
				student.setTeam(team);
			}
			
			student = studentService.create(student);
		} catch (Exception ex) {
			ex.printStackTrace();
			return "Error creating the student: " + ex.toString();
		}
		return "Student succesfully created! (id = " + student.getId() + ")";
	}

	@RequestMapping("/delete")
	@ResponseBody
	public String delete(long id) {
		try {
			studentService.delete(id);
		} catch (Exception ex) {
			return "Error deleting the student: " + ex.toString();
		}
		return "Student succesfully deleted!";
	}

	@RequestMapping("/get-by-name")
	@ResponseBody
	public String getByName(String name) {
		Student student;
		try {
			student = studentService.findByName(name);
		} catch (Exception ex) {
			return "Studet not found";
		}
		return "The Student info is: " + student;
	}

	@RequestMapping("/update")
	@ResponseBody
	public String update(@NotNull long id, String name, String teamName) {
		try {
			Student student = new Student(name);
			student.setId(id);
			student.setName(name);

			if (!StringUtils.isEmpty(teamName)) {
				Team team = new Team(teamName);
				student.setTeam(team);
			}

			studentService.update(student);
		} catch (Exception ex) {
			ex.printStackTrace();
			
			return "Error updating the student: " + ex.toString();
		}
		return "Student succesfully updated!";
	}

	@RequestMapping("/get")
	@ResponseBody
	public String getAll() {
		List<Student> students = null;
		try {
			students = studentService.findAll();
		} catch (Exception ex) {
			return "Student not found";
		}
		return "The student list is: " + students;
	}

	@RequestMapping("/get/{id}")
	@ResponseBody
	public String getById(@PathVariable("id") long id) {
		Student student = null;
		try {
			student = studentService.findById(id);
		} catch (Exception ex) {
			return "Student not found";
		}
		return "The student info is: " + student + "\n team is: "+student.getTeam();
	}
	
	@Resource
	private StudentRepository studentRepository;
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public String filter(@QuerydslPredicate(root = Student.class) Predicate predicate, Pageable pageable,
			@RequestParam MultiValueMap<String, String> parameters) {
		
		String result = "";
		
		for(Iterator<Student> studentItr = studentRepository.findAll(predicate, pageable).iterator(); studentItr.hasNext();) {
			Student student = studentItr.next();
			System.out.println("$$$$$$$$$$$$ : "+student.toString());
			result += student.toString();
		}
		
		return result;
	}

}
