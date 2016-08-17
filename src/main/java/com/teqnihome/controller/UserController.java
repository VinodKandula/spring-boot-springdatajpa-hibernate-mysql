package com.teqnihome.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.teqnihome.model.User;
import com.teqnihome.service.UserService;

/**
 *
 * @author Vinod
 */
@Controller
@RequestMapping(value="/user")
public class UserController {
	

	@Autowired
	private UserService userService;

	@RequestMapping("/create")
	@ResponseBody
	public String create(String email, String name) {
		User user = null;
		try {
			user = new User(email, name);
			userService.create(user);
		} catch (Exception ex) {
			return "Error creating the user: " + ex.toString();
		}
		return "User succesfully created! (id = " + user.getId() + ")";
	}

	@RequestMapping("/delete")
	@ResponseBody
	public String delete(long id) {
		try {
			userService.delete(id);
		} catch (Exception ex) {
			return "Error deleting the user: " + ex.toString();
		}
		return "User succesfully deleted!";
	}

	@RequestMapping("/get-by-email")
	@ResponseBody
	public String getByEmail(String email) {
		String userId;
		try {
			User user = userService.findByEmail(email);
			userId = String.valueOf(user.getId());
		} catch (Exception ex) {
			return "User not found";
		}
		return "The user id is: " + userId;
	}

	@RequestMapping("/update")
	@ResponseBody
	public String updateUser(long id, String email, String name) {
		try {
			User user = new User(email, name);
			user.setId(id);
			userService.update(user);
		} catch (Exception ex) {
			return "Error updating the user: " + ex.toString();
		}
		return "User succesfully updated!";
	}

	@RequestMapping("/get")
	@ResponseBody
	public String getAll() {
		List<User> users = null;
		try {
			Iterable<User> userItr = userService.findAll();
			users = new ArrayList<>();
			userItr.forEach(users::add);
		} catch (Exception ex) {
			return "Users not found";
		}
		return "The user list is: " + users;
	}
	
	@RequestMapping("/get/{id}")
	@ResponseBody
	public String getById(@PathVariable("id") long id) {
		User user = null;
		try {
			user = userService.findById(id);
		} catch (Exception ex) {
			return "User not found";
		}
		return "The user info is: " + user;
	}

}
