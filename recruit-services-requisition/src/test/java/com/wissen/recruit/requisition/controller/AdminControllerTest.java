package com.wissen.recruit.requisition.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wissen.recruit.requisition.constants.ErrorMessages;
import com.wissen.recruit.requisition.dto.AdminWorkFlowDTO;
import com.wissen.recruit.requisition.dto.RequisitionDTO;
import com.wissen.recruit.requisition.entity.AdminWorkFlow;
import com.wissen.recruit.requisition.entity.HiringProcess;
import com.wissen.recruit.requisition.entity.JobDescription;
import com.wissen.recruit.requisition.entity.LocationWiseOpenings;
import com.wissen.recruit.requisition.entity.Requisition;
import com.wissen.recruit.requisition.exception.InvalidIdException;
import com.wissen.recruit.requisition.mapper.AdminMapper;
import com.wissen.recruit.requisition.mapper.RequisitionMapper;
import com.wissen.recruit.requisition.service.AdminService;
import com.wissen.recruit.requisition.service.RequisitionService;

@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	AdminService service;

	AdminWorkFlow workflow;
	AdminWorkFlowDTO dto;
	
	List<AdminWorkFlowDTO> dtolist = new ArrayList<>();

	@BeforeEach
	void init() {
		workflow = AdminWorkFlow.builder().workFlowId(1).workFlowName("new Flow").workFlowSteps("steps").build();
		dto = AdminMapper.workflowModelToDTO(workflow);
		dtolist.add(dto);
	}

	@Test
	void test_SaveWorkflow_Success() throws Exception {
		when(service.saveworkflow(dto)).thenReturn(dto);
		mockMvc.perform(MockMvcRequestBuilders.post("/requisition/admin/workflow").content(new ObjectMapper().writeValueAsString(dto))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}
	
	@Test
	void test_SaveWorkflow_Fail_Return400() throws Exception {
		dto.setWorkFlowName("");
		when(service.saveworkflow(dto)).thenReturn(dto);
		mockMvc.perform(
				MockMvcRequestBuilders.post("/requisition/admin/workflow").content(new ObjectMapper().writeValueAsString(new AdminWorkFlowDTO()))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(400));
	}

	@Test
	void test_getAllWorkflows_Success() throws Exception {
		when(service.getAllWorkflows()).thenReturn(dtolist);
		mockMvc.perform(MockMvcRequestBuilders.get("/requisition/admin/workflow/").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void test_getWorkflowById_Success() throws Exception {
		when(service.getWorkflowById(1)).thenReturn(dto);
		mockMvc.perform(MockMvcRequestBuilders.get("/requisition/admin/workflow/1").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

	}

	@Test
	void test_getWorkflowById_Fail() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/requisition/admin/workflow/-1").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidIdException))
				.andExpect(result -> assertEquals(ErrorMessages.INVALID_WORKFLOW_ID+"-1",
						result.getResolvedException().getMessage()));
	}

	@Test
	void test_deleteWorkflowById_Success() throws Exception {
		when(service.deleteWorkFlowById(1)).thenReturn(dto);
		mockMvc.perform(MockMvcRequestBuilders.delete("/requisition/admin/workflow/1").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void test_deleteWorkflowById_Fail() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/requisition/admin/workflow/-1").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidIdException))
				.andExpect(result -> assertEquals(ErrorMessages.INVALID_WORKFLOW_ID+"-1",
						result.getResolvedException().getMessage()));
	}

	@Test
	void test_updateWorkflow_Success() throws Exception {
		when(service.updateWorkflow(dto)).thenReturn(dto);
		mockMvc.perform(MockMvcRequestBuilders.put("/requisition/admin/workflow/").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(dto)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void test_updateRequisition_Fail() throws Exception {
		dto.setWorkFlowId(-1);
		mockMvc.perform(MockMvcRequestBuilders.put("/requisition/admin/workflow/").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(dto)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidIdException))
				.andExpect(result -> assertEquals(ErrorMessages.INVALID_WORKFLOW_ID+"-1",
						result.getResolvedException().getMessage()));
	}
	
}
