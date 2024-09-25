package com.wissen.recruit.requisition.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.wissen.recruit.requisition.entity.Designation;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DataJpaTest
class DesignationRepositoryTest {

	@Autowired
	DesignationRepository designationRepository;

	Designation designationDetails;
	Designation designationDetails1;

	List<Designation> designationsList = new ArrayList<>();

	@BeforeEach
	void init() {
		designationDetails = Designation.builder().value("Foo").label("Foo").build();

		designationDetails1 = Designation.builder().value("Bar").label("Bar").build();
		designationsList.add(designationDetails);
		designationsList.add(designationDetails1);

		designationRepository.saveAll(designationsList);

	}

	@Test
	void test_getAllDesignations_success() {
		List<Designation> actualResult = designationRepository.findAll();
		assertThat(actualResult).isNotNull().hasSize(2);
		assertThat(actualResult.get(1).getValue()).isEqualTo("Bar");
	}

	@Test
	void test_getAllDesignations_Fail() {
		designationRepository.deleteAll();
		List<Designation> actualResult = designationRepository.findAll();
		assertThat(actualResult).isEmpty();
	}

	@Test
	void test_createDesignation() {
		Designation expectedResults = designationRepository.save(Designation.builder().value("Foo").label("Foo").build());
		assertThat(expectedResults).isNotNull();
		assertThat(expectedResults.getDesignationId()).isPositive();
		assertThat(expectedResults.getValue()).isEqualTo("Foo");
	}
}
