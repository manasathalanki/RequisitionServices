package com.wissen.recruit.requisition.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.wissen.recruit.requisition.dto.AdminWorkFlowDTO;
import com.wissen.recruit.requisition.dto.RequisitionDTO;
import com.wissen.recruit.requisition.entity.AdminWorkFlow;
import com.wissen.recruit.requisition.entity.HiringProcess;
import com.wissen.recruit.requisition.entity.JobDescription;
import com.wissen.recruit.requisition.entity.LocationWiseOpenings;
import com.wissen.recruit.requisition.entity.Requisition;
import com.wissen.recruit.requisition.exception.RequisitionNotFoundException;
import com.wissen.recruit.requisition.exception.WorkflowNotFoundException;
import com.wissen.recruit.requisition.mapper.AdminMapper;
import com.wissen.recruit.requisition.mapper.RequisitionMapper;
import com.wissen.recruit.requisition.repository.AdminWorkFlowRepository;
import com.wissen.recruit.requisition.repository.RequisitionRepository;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {

	@Mock
	private AdminWorkFlowRepository repository;
	
	@InjectMocks
	private AdminServiceImpl service;
	
	AdminWorkFlow workflow;
	AdminWorkFlowDTO dto;
	
	List<AdminWorkFlowDTO> dtolist = new ArrayList<>();
	List<AdminWorkFlow> workflowlist = new ArrayList<>();
	
	
	@BeforeEach
	void init() {
		workflow = AdminWorkFlow.builder().workFlowId(1).workFlowName("new Flow").workFlowSteps("steps").build();
		dto = AdminMapper.workflowModelToDTO(workflow);
		workflowlist.add(workflow);
		dtolist.add(dto);
	}
	
	@Test
	void test_SaveWorkFlow_Success() {
		when(repository.save(workflow)).thenReturn(workflow);
		AdminWorkFlowDTO actualResult = service.saveworkflow(dto);
		assertThat(actualResult.getWorkFlowId()).isPositive();
		assertThat(actualResult.getWorkFlowName()).isEqualTo("new Flow");
		assertThat(actualResult.getWorkFlowSteps()).isEqualTo("steps");
		
	}
	
	@Test
	void test_getAllWorkflows() {
		when(repository.findAll()).thenReturn(workflowlist);
		List<AdminWorkFlowDTO> actualResult = service.getAllWorkflows();
		assertThat(actualResult).isNotNull().hasSize(1);
	}
	
	@Test
	void test_getAllWorkflows_returnsEmptyList() {
		when(repository.findAll()).thenReturn(new ArrayList<>());
		List<AdminWorkFlowDTO> actualResult = service.getAllWorkflows();		
		assertThat(actualResult).isEmpty();
	}
	
	@Test
	void test_getWorkflowById_Success() {
		when(repository.findById(1)).thenReturn(Optional.of(workflow));
		AdminWorkFlowDTO expectedResult = service.getWorkflowById(1);
		 
		assertThat(expectedResult).isNotNull();
		assertThat(expectedResult.getWorkFlowId()).isPositive();
		assertThat(expectedResult.getWorkFlowName()).isEqualTo("new Flow");
		assertThat(expectedResult.getWorkFlowSteps()).isEqualTo("steps");
	}
	
	@Test
	void test_getWorkflowById_Thorw_WorkFlowNotFoundException() {
		when(repository.findById(-1)).thenThrow(WorkflowNotFoundException.class);
		assertThrows(WorkflowNotFoundException.class, () -> {service.getWorkflowById(-1); });
	}
	
	@Test
	void test_DeleteWorkflowById_Success() {
		when(repository.findById(1)).thenReturn(Optional.of(workflow));
		doNothing().when(repository).deleteById(1);
		
		 AdminWorkFlowDTO expectedResult = service.deleteWorkFlowById(1);
		 verify(repository, times(1)).deleteById(1);
		 verifyNoMoreInteractions(repository);
		 
		 assertThat(expectedResult).isNotNull();
		 assertThat(expectedResult.getWorkFlowId()).isPositive();
		 assertThat(expectedResult.getWorkFlowName()).isEqualTo("new Flow");
	}
	
	@Test
	void test_DeleteWorkflowById_Thorw_WorkflowNotFoundException() {	
		 Throwable exception = assertThrows(
				 WorkflowNotFoundException.class, () -> {
					 service.deleteWorkFlowById(0);
		            }
		    );
		 assertEquals("Workflow details not found for the id 0", exception.getMessage());
	}
	
	@Test
	void test_UpdateWorkflow_Success() {
		
		when(repository.findById(1)).thenReturn(Optional.of(workflow));
		
		workflow.setWorkFlowName("Test Flow");
		dto.setWorkFlowName("Test Flow");
		when(repository.save(workflow)).thenReturn(workflow);
		
		AdminWorkFlowDTO actualResult = service.updateWorkflow(dto);
		
		assertThat(actualResult).isNotNull();
		assertThat(actualResult.getWorkFlowName()).isEqualTo("Test Flow");
		assertThat(actualResult.getWorkFlowSteps()).isEqualTo("steps");
	}
	
	@Test
	void test_Updateworkflow_Thorw_WorkflowNotFoundException() {
		dto.setWorkFlowId(0);
		when(repository.findById(0)).thenThrow(WorkflowNotFoundException.class);
		
		assertThrows(WorkflowNotFoundException.class, () -> {
			 service.updateWorkflow(dto);
		 });
		verify(repository, never()).save(workflow);
	}
}
