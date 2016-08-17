package com.teqnihome.controller;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.teqnihome.model.Team;
import com.teqnihome.service.TeamService;

/**
 *
 * @author Vinod
 */
@Controller
@RequestMapping(value = "/team")
public class TeamController {

	@Autowired
	private TeamService teamService;

	@RequestMapping("/create")
	@ResponseBody
	public String create(String name) {
		Team team = null;
		try {
			team = new Team(name);
			teamService.create(team);
		} catch (Exception ex) {
			return "Error creating the team: " + ex.toString();
		}
		return "Team succesfully created! (id = " + team.getId() + ")";
	}

	@RequestMapping("/delete")
	@ResponseBody
	public String delete(long id) {
		try {
			teamService.delete(id);
		} catch (Exception ex) {
			return "Error deleting the team: " + ex.toString();
		}
		return "Team succesfully deleted!";
	}

	@RequestMapping("/get-by-name")
	@ResponseBody
	public String getByName(String name) {
		Team team;
		try {
			team = teamService.findByName(name);
		} catch (Exception ex) {
			return "Team not found";
		}
		return "The Team info is: " + team;
	}

	@RequestMapping("/update")
	@ResponseBody
	public String update(@NotNull long id, String name) {
		try {
			Team team = new Team(name);
			team.setId(id);

			if (!StringUtils.isEmpty(name)) {
				team.setName(name);
			}

			teamService.update(team);
		} catch (Exception ex) {
			return "Error updating the team: " + ex.toString();
		}
		return "Team succesfully updated!";
	}

	@RequestMapping("/get")
	@ResponseBody
	public String getAll() {
		List<Team> teams = null;
		try {
			teams = teamService.findAll();
		} catch (Exception ex) {
			return "Team not found";
		}
		return "The team list is: " + teams;
	}

	@RequestMapping("/get/{id}")
	@ResponseBody
	public String getById(@PathVariable("id") long id) {
		Team team = null;
		try {
			team = teamService.findById(id);
		} catch (Exception ex) {
			return "Team not found";
		}
		return "The team info is: " + team + "\n student set is: "+team.getStudents();
	}

}
