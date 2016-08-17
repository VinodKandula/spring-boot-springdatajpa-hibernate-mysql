package com.teqnihome.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teqnihome.model.User;

/**
 * 
 * @author Vinod
 */
public interface UserRepository extends JpaRepository<User, Long> {

  public User findByEmail(String email);

}
