package com.wissen.recruit.requisition.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.wissen.recruit.requisition.entity.Band;
import com.wissen.recruit.requisition.entity.Designation;
import com.wissen.recruit.requisition.entity.Grade;
import com.wissen.recruit.requisition.entity.HiringManager;
import com.wissen.recruit.requisition.entity.InterviewPanel;
import com.wissen.recruit.requisition.entity.JobLocation;
import com.wissen.recruit.requisition.entity.Skills;
import com.wissen.recruit.requisition.service.DropDownService;

@SpringBootTest
@AutoConfigureMockMvc
class DropDownControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	DropDownService service;
	
	@BeforeEach
	void init() {
		
	}
	
	@Test
	void test_getSkillsDropdown_Success() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/requisition/dropdown/skill").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	@Test
	void test_getJobLocationsDropdown_Success() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/requisition/dropdown/joblocation").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	@Test
	void test_getInterviewPanelDropdown_Success() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/requisition/dropdown/interviewpanel").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	@Test
	void test_getHiringManagerDropdown_Success() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/requisition/dropdown/hiringmanager").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	@Test
	void test_getAllBandDetails_Success() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/requisition/dropdown/band").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void test_getAllgradeDetails_Success() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/requisition/dropdown/grade").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void test_getAllDesignationDetails_Success() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/requisition/dropdown/designation").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	@Test
	void test_CreateBand_Success() throws Exception {
		Band band = Band.builder().value("Band").label("Band").build();
		when(service.createBand(band)).thenReturn(band);
		mockMvc.perform(MockMvcRequestBuilders.post("/requisition/dropdown/band").content(new ObjectMapper().writeValueAsString(band))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}
	@Test
	void test_CreateBand_Fail_Return400() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/requisition/dropdown/band").content(new ObjectMapper().writeValueAsString(new Band()))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(400));
	}
	
	@Test
	void test_CreateGrade_Success() throws Exception {
		Grade grade = Grade.builder().value("Grade").label("Grade").build();
		when(service.createGrade(grade)).thenReturn(grade);
		mockMvc.perform(MockMvcRequestBuilders.post("/requisition/dropdown/grade").content(new ObjectMapper().writeValueAsString(grade))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}
	@Test
	void test_CreateGrade_Fail_Return400() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/requisition/dropdown/grade").content(new ObjectMapper().writeValueAsString(new Grade()))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(400));
	}
	
	@Test
	void test_CreateDesignation_Success() throws Exception {
		Designation designation = Designation.builder().value("Designation").label("Designation").build();
		when(service.createDesignation(designation)).thenReturn(designation);
		mockMvc.perform(MockMvcRequestBuilders.post("/requisition/dropdown/designation").content(new ObjectMapper().writeValueAsString(designation))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}
	@Test
	void test_CreateDesignation_Fail_Return400() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/requisition/dropdown/designation").content(new ObjectMapper().writeValueAsString(new Designation()))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(400));
	}
	
	@Test
	void test_CreateJobLocation_Success() throws Exception {
		JobLocation location = JobLocation.builder().value("Location").label("Location").build();
		when(service.createJobLocation(location)).thenReturn(location);
		mockMvc.perform(MockMvcRequestBuilders.post("/requisition/dropdown/joblocation").content(new ObjectMapper().writeValueAsString(location))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}
	
	@Test
	void test_CreateJobLocation_Fail_Return400() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/requisition/dropdown/joblocation").content(new ObjectMapper().writeValueAsString(new JobLocation()))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(400));
	}
	
	@Test
	void test_CreateHiringManager_Success() throws Exception {
		HiringManager manager = HiringManager.builder().value("Manager").label("Manager").build();
		when(service.createHiringManager(manager)).thenReturn(manager);
		mockMvc.perform(MockMvcRequestBuilders.post("/requisition/dropdown/hiringmanager").content(new ObjectMapper().writeValueAsString(manager))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}
	@Test
	void test_CreateHiringManager_Fail_Return400() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/requisition/dropdown/hiringmanager").content(new ObjectMapper().writeValueAsString(new HiringManager()))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(400));
	}
	
	@Test
	void test_CreateInterviewPanel_Success() throws Exception {
		InterviewPanel panel = InterviewPanel.builder().label("Panel").value("Panel").build();
		when(service.createInterviewPanel(panel)).thenReturn(panel);
		mockMvc.perform(MockMvcRequestBuilders.post("/requisition/dropdown/interviewpanel").content(new ObjectMapper().writeValueAsString(panel))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}
	@Test
	void test_CreateInterviewPanel_Fail_Return400() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/requisition/dropdown/interviewpanel").content(new ObjectMapper().writeValueAsString(new InterviewPanel()))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(400));
	}
	
	@Test
	void test_CreateSkills_Success() throws Exception {
		Skills skill = Skills.builder().value("SKill").label("SKill").build();
		when(service.createSkills(skill)).thenReturn(skill);
		mockMvc.perform(MockMvcRequestBuilders.post("/requisition/dropdown/skill").content(new ObjectMapper().writeValueAsString(skill))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}
	@Test
	void test_CreateSkills_Fail_Return400() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/requisition/dropdown/skill").content(new ObjectMapper().writeValueAsString(new Skills()))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(400));
	}

}
