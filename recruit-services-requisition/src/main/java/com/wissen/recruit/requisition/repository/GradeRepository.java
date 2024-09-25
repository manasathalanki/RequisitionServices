package com.wissen.recruit.requisition.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wissen.recruit.requisition.entity.Grade;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Integer> {

}
