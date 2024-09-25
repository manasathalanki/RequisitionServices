package com.wissen.recruit.requisition.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wissen.recruit.requisition.entity.Designation;

@Repository
public interface DesignationRepository extends JpaRepository<Designation, Integer> {

}
