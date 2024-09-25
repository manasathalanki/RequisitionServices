package com.wissen.recruit.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wissen.recruit.core.entity.User;

@Repository
public interface SuperAdminRepository extends JpaRepository<User, String>{

}
