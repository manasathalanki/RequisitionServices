package com.wissen.recruit.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wissen.recruit.core.entity.Privilege;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

	Privilege findByName(String privilegeName);

}
