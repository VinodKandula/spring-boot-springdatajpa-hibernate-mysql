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

import com.teqnihome.model.User;
import com.teqnihome.repository.UserRepository;
import com.teqnihome.service.generic.GenericServiceImpl;

/**
 * @author vinod
 *
 */
@Service
@Transactional
public class UserServiceImpl extends GenericServiceImpl<User, Long> implements UserService {
	
	@Resource
	private UserRepository userRepository;
	
	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	@Transactional(rollbackFor=EntityNotFoundException.class)
	public User update(User user) throws EntityNotFoundException {
		User updateUser = userRepository.findOne(user.getId());
		if (updateUser == null)
			throw new EntityNotFoundException();
		
		if (!StringUtils.isEmpty(user.getEmail())) {
			updateUser.setEmail(user.getEmail());
		}
		if (!StringUtils.isEmpty(user.getName())) {
			updateUser.setName(user.getName());
		}
		
		return userRepository.save(updateUser);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User queryDSL(String expression) {
		// TODO Auto-generated method stub
		return null;
	}

}
