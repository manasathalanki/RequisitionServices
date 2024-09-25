package com.wissen.recruit.requisition.repository;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.wissen.recruit.requisition.entity.AdminWorkFlow;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DataJpaTest
class AdminWorkFlowRepositoryTest {

	@Autowired
	private AdminWorkFlowRepository repository;

	AdminWorkFlow workflow;
	List<AdminWorkFlow> workflowlist = new ArrayList<>();


	@BeforeEach
	void init() {
		workflow = AdminWorkFlow.builder().workFlowId(1).workFlowName("New Flow").workFlowSteps("Steps").build();
	}

	@AfterEach
	void destroyAll() {
		repository.deleteAll();
	}

	@Test
	void test_SaveWorkFlow_Success() {
		AdminWorkFlow actualResult = repository.save(workflow);

		assertThat(actualResult).isNotNull();
		assertThat(actualResult.getWorkFlowId()).isPositive();
		assertThat(actualResult.getWorkFlowName()).isEqualTo("New Flow");
		assertThat(actualResult.getWorkFlowSteps()).isEqualTo("Steps");
	}

	@Test
	void test_getAllWorkflows_success() {
		repository.save(workflow);
		List<AdminWorkFlow> actualResult = repository.findAll();
		assertThat(actualResult).isNotNull().hasSize(1);
	}

	@Test
	void test_getAllWorkflows_returns_emptyList() {
		repository.deleteAll();
		List<AdminWorkFlow> actualResult = repository.findAll();
		assertThat(actualResult).isNotNull().isEmpty();
	}

	@Test
	void test_getWorkflowById_Success() {
		AdminWorkFlow workflowfromdb = repository.save(workflow);
		Optional<AdminWorkFlow> actualResult = repository.findById(workflowfromdb.getWorkFlowId());
		assertThat(actualResult).isNotNull().isNotEmpty();
		assertThat(actualResult.get().getWorkFlowName()).isEqualTo("New Flow");
		assertThat(actualResult.get().getWorkFlowSteps()).isEqualTo("Steps");		
	}

	@Test
	void test_getWorkflowById_returns_emptyList() {
		Optional<AdminWorkFlow> actualResult = repository.findById(123456);
		assertThat(actualResult).isEmpty().isNotNull();
	}

	@Test
	void test_deleteWorkflowById_Success() {
		AdminWorkFlow workflowfromdb = repository.save(workflow);

		repository.deleteById(workflowfromdb.getWorkFlowId());
		assertThat(repository.findAll()).isEmpty();
	}

	@Test
	void test_Updateworkflow_Success() {
		AdminWorkFlow flow = repository.save(workflow);

		AdminWorkFlow savedrequisition = repository.findById(flow.getWorkFlowId()).get();
		savedrequisition.setWorkFlowName("Test Flow New");

		AdminWorkFlow updatedflow = repository.save(savedrequisition);

		assertThat(updatedflow.getWorkFlowId()).isEqualTo(savedrequisition.getWorkFlowId());
		assertThat(updatedflow.getWorkFlowName()).isEqualTo("Test Flow New");
		assertThat(updatedflow.getWorkFlowSteps()).isEqualTo(savedrequisition.getWorkFlowSteps());
	}

}
