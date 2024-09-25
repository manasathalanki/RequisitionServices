package com.wissen.recruit.requisition.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.wissen.recruit.requisition.entity.Grade;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DataJpaTest
class GradeRepositoryTest {

	@Autowired
	GradeRepository gradeRepository;

	Grade gradeDetails;

	@BeforeEach
	void init() {
		gradeDetails = Grade.builder().value("Foo").label("Foo").build();
		gradeRepository.save(gradeDetails);

	}

	@Test
	void test_getAllGrades_success() {
		List<Grade> actualResult = gradeRepository.findAll();
		assertThat(actualResult).isNotNull().hasSize(1);
		assertThat(actualResult.get(0).getValue()).isNotNull();
	}

	@Test
	void test_getAllGrades_Fail() {
		gradeRepository.deleteAll();
		List<Grade> actualResult = gradeRepository.findAll();
		assertThat(actualResult).isEmpty();
	}

	@Test
	void test_createGrade() {
		Grade expectedResults = gradeRepository.save(Grade.builder().value("Foo").label("Foo").build());
		assertThat(expectedResults).isNotNull();
		assertThat(expectedResults.getGradeId()).isPositive();
		assertThat(expectedResults.getValue()).isEqualTo("Foo");
	}
}
