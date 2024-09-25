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

import com.wissen.recruit.requisition.entity.InterviewPanel;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DataJpaTest
class InterviewPanelRepositoryTest {

	@Autowired
	private InterviewPanelRepository repository;

	@BeforeEach
	void init() {
		repository.save(InterviewPanel.builder().value("Foo").label("Foo").build());
		repository.save(InterviewPanel.builder().value("Bar").label("Bar").build());
	}

	@Test
	void test_getInterviewPanelInfo_success() {
		List<InterviewPanel> actualResult = repository.findAll();
		assertThat(actualResult).isNotNull().hasSize(2);

	}

	@Test
	void test_getInterviewPanelInfo_Fail() {
		repository.deleteAll();
		List<InterviewPanel> actualResult = repository.findAll();
		assertThat(actualResult).isNotNull().isEmpty();
	}
	
	@Test
	void test_saveInterviewPanle() {
		InterviewPanel expectedResults = repository.save(InterviewPanel.builder().value("Foo").label("Foo").build());
		assertThat(expectedResults).isNotNull();
		assertThat(expectedResults.getId()).isPositive();
		assertThat(expectedResults.getValue()).isEqualTo("Foo");
	}
}
