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

import com.teqnihome.model.Team;
import com.teqnihome.repository.TeamRepository;
import com.teqnihome.service.generic.GenericServiceImpl;

/**
 * @author vinod
 *
 */
@Service
@Transactional
public class TeamServiceImpl extends GenericServiceImpl<Team, Long> implements TeamService {

	@Resource
	TeamRepository  teamRepository;
	
	@Override
	public List<Team> findAll() {
		return teamRepository.findAll();
	}

	@Override
	@Transactional(rollbackFor=EntityNotFoundException.class)
	public Team update(Team team) throws EntityNotFoundException {
		Team updateTeam = teamRepository.findOne(team.getId());
		if (updateTeam == null)
			throw new EntityNotFoundException();

		if (!StringUtils.isEmpty(team.getName())) {
			updateTeam.setName(team.getName());
		}
		
		return teamRepository.save(updateTeam);
	}

	@Override
	public Team findByName(String name) {
		return teamRepository.findByName(name);
	}

	@Override
	public Team queryDSL(String expression) {
		// TODO Auto-generated method stub
		return null;
	}

}
