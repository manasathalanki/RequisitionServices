package com.wissen.recruit.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wissen.recruit.core.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

	Optional<User> findByUsername(String username);

	Optional<User> findByEmail(String email);
	
	Optional<User> findByPhone(String phone);
	
	
}
