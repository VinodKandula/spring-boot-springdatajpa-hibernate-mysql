/**
 * 
 */
package com.teqnihome.service;

import com.teqnihome.model.Team;
import com.teqnihome.service.generic.GenericService;

/**
 * @author vinod
 *
 */
public interface TeamService extends GenericService<Team, Long>{

	public Team findByName(String name); 
	
}
