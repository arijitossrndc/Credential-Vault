package com.epam.pwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.pwt.entity.User;


public interface UserRepository extends JpaRepository<User,Integer>{
	

	Optional<User> findById(int userId);
	
	Optional<User> findByUsername(String username);

	int countByUsername(String username);
	
	int countByEmail(String email);


}
