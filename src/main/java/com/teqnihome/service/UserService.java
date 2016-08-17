/**
 * 
 */
package com.teqnihome.service;

import com.teqnihome.model.User;
import com.teqnihome.service.generic.GenericService;

/**
 * @author vinod
 *
 */
public interface UserService extends GenericService<User, Long> {
	
	public User findByEmail(String email); 

}
