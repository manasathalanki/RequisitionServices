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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wissen.recruit.requisition.constants.ErrorMessages;
import com.wissen.recruit.requisition.dto.RequisitionDTO;
import com.wissen.recruit.requisition.entity.HiringProcess;
import com.wissen.recruit.requisition.entity.JobDescription;
import com.wissen.recruit.requisition.entity.LocationWiseOpenings;
import com.wissen.recruit.requisition.entity.Requisition;
import com.wissen.recruit.requisition.exception.InvalidIdException;
import com.wissen.recruit.requisition.mapper.RequisitionMapper;
import com.wissen.recruit.requisition.service.RequisitionService;

@SpringBootTest
@AutoConfigureMockMvc
class RequisistionControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	RequisitionService requisitionService;

	Requisition details;
	HiringProcess hiringprocess;
	JobDescription jobDesc;
	LocationWiseOpenings openings;
	
	List<Requisition> requisitionsList = new ArrayList<>();
	List<RequisitionDTO> dtolist = new ArrayList<>();
	List<LocationWiseOpenings> openingsList = new ArrayList<>();

	@BeforeEach
	void init() {
		
		openingsList.add(LocationWiseOpenings.builder().location("Hyd").openings(4).build());
		openingsList.add(LocationWiseOpenings.builder().location("BLR").openings(3).build());
		
		hiringprocess = HiringProcess.builder().screeningRPS("test").isScreeningSelected(true).shortlistingRPS("test").isShortlistingSelected(true)
				.qualAssessRPS("test").isQualAssessSelected(true).behavAssessRPS("test").isBehavAssessSelected(true).vidAssessRPS("test").isVidAssessSelected(true)
				.interview1RPS("test").interview2RPS("test").hrInterviewRPS("test").isHrInterviewSelected(true).isInterview1Selected(true).isInterview2Selected(true)
				.documentationRPS("test").isDocumentationSelected(true).offerRPS("test").isOfferSelected(true).build();
		
		jobDesc = JobDescription.builder().education("Degree").description("desc").rolesAndResponsibilty("roles")
				.primarySkills("primary").secondarySkills("secondary").additionalSkills("additional").build();

		details = Requisition.builder().band("5L").closingDate(new Date()).minExperience("2").maxExperience("4").grade("4")
				.isCampus(false).tatDays("5").startDate(new Date()).requisitionType("Normal").jobTitle("Test Engineer").hiringProcess(hiringprocess)
				.jobDescription(jobDesc).maxCtc("10L").minCtc("4L").client("Client").functionalArea("SET")
				.team("team").project("project").hiringManager("foo").locationWiseOpeningsList(openingsList).build();

		requisitionsList.add(details);
		dtolist.add(RequisitionMapper.modeltoDTO(details));
	}

	@Test
	void test_getPortNumber() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/requisition/port")).andExpect(status().isOk());
	}

	@Test
	void test_CreateRequisition_Success() throws Exception {
		when(requisitionService.createRequisition(RequisitionMapper.modeltoDTO(details))).thenReturn(RequisitionMapper.modeltoDTO(details));
		mockMvc.perform(MockMvcRequestBuilders.post("/requisition/").content(new ObjectMapper().writeValueAsString(details))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}

	@Test
	void test_CreateRequisition_Success_WithoutchildEntityInfo() throws Exception {
		details.setHiringProcess(new HiringProcess());
		when(requisitionService.createRequisition(RequisitionMapper.modeltoDTO(details))).thenReturn(RequisitionMapper.modeltoDTO(details));
		mockMvc.perform(MockMvcRequestBuilders.post("/requisition/").content(new ObjectMapper().writeValueAsString(details))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}

	@Test
	void test_CreateRequisition_Fail_Return400() throws Exception {
		when(requisitionService.createRequisition(RequisitionMapper.modeltoDTO(details))).thenReturn(RequisitionMapper.modeltoDTO(details));
		mockMvc.perform(
				MockMvcRequestBuilders.post("/requisition/").content(new ObjectMapper().writeValueAsString(new Requisition()))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(400));
	}

	@Test
	void test_getAllRequisitions_Success() throws Exception {
		when(requisitionService.getAllRequisitions()).thenReturn(dtolist);
		mockMvc.perform(MockMvcRequestBuilders.get("/requisition/").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void test_getRequisitionById_Success() throws Exception {
		when(requisitionService.getRequisitionById(1)).thenReturn(RequisitionMapper.modeltoDTO(details));
		mockMvc.perform(MockMvcRequestBuilders.get("/requisition/1").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

	}

	@Test
	void test_getRequisitionById_Fail() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/requisition/-1").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidIdException))
				.andExpect(result -> assertEquals(ErrorMessages.INVALID_REQUISITION_ID+"-1",
						result.getResolvedException().getMessage()));
	}

	@Test
	void test_deleteRequisitionById_Success() throws Exception {
		when(requisitionService.deleteRequisitionById(1)).thenReturn(RequisitionMapper.modeltoDTO(details));
		mockMvc.perform(MockMvcRequestBuilders.delete("/requisition/1").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void test_deleteRequisitionById_Fail() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/requisition/-1").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidIdException))
				.andExpect(result -> assertEquals(ErrorMessages.INVALID_REQUISITION_ID+"-1",
						result.getResolvedException().getMessage()));
	}

	@Test
	void test_updateRequisition_Success() throws Exception {
		details.setRequisitionId(1);
		when(requisitionService.updateRequisition(RequisitionMapper.modeltoDTO(details))).thenReturn(RequisitionMapper.modeltoDTO(details));
		mockMvc.perform(MockMvcRequestBuilders.put("/requisition/").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(details)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void test_updateRequisition_Success_WithOutChildEntityInfo() throws Exception {
		details.setRequisitionId(1);
		details.setHiringProcess(null);
		when(requisitionService.updateRequisition(RequisitionMapper.modeltoDTO(details))).thenReturn(RequisitionMapper.modeltoDTO(details));
		mockMvc.perform(MockMvcRequestBuilders.put("/requisition/").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(details)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void test_updateRequisition_Fail() throws Exception {
		details.setRequisitionId(-1);
		mockMvc.perform(MockMvcRequestBuilders.put("/requisition/").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(details)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidIdException))
				.andExpect(result -> assertEquals(ErrorMessages.INVALID_REQUISITION_ID+"-1",
						result.getResolvedException().getMessage()));
	}
	
}
