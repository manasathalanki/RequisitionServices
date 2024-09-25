package com.wissen.recruit.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wissen.recruit.core.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByName(String roleName);

}
