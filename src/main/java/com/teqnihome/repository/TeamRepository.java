/**
 * 
 */
package com.teqnihome.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teqnihome.model.Team;

/**
 * @author vinod
 *
 */
@Transactional
public interface TeamRepository extends JpaRepository<Team, Long>{

	public Team findByName(String name);
	
}
