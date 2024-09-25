package com.wissen.recruit.requisition.controller;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.wissen.recruit.requisition.dto.CandidateDTO;
import com.wissen.recruit.requisition.entity.Candidate;
import com.wissen.recruit.requisition.entity.HiringProcess;
import com.wissen.recruit.requisition.entity.JobDescription;
import com.wissen.recruit.requisition.entity.LocationWiseOpenings;
import com.wissen.recruit.requisition.entity.Requisition;
import com.wissen.recruit.requisition.exception.CandidateNotFoundException;
import com.wissen.recruit.requisition.exception.UsernameNotFoundException;
import com.wissen.recruit.requisition.mapper.CandidateDTOMapper;
import com.wissen.recruit.requisition.mapper.CandidateModelMapper;
import com.wissen.recruit.requisition.service.CandidateService;

@ExtendWith(MockitoExtension.class)
class CandidateControllerTest {

	@InjectMocks
	CandidateController candidateController;

	@Mock
	CandidateService candidateService;

	ResponseEntity<CandidateDTO> candidateDTO;

	Candidate candidate;
	Candidate foundCand;
	CandidateDTO dto;

	Requisition requisition;
	HiringProcess hiringprocess;
	JobDescription jobDesc;
	LocationWiseOpenings openings;
	
	List<LocationWiseOpenings> openingsList = new ArrayList<>();
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	void setup() throws Exception {
		MockitoAnnotations.openMocks(this);
		
		openingsList.add(LocationWiseOpenings.builder().location("Hyd").openings(4).build());
		openingsList.add(LocationWiseOpenings.builder().location("BLR").openings(3).build());
		
		hiringprocess = HiringProcess.builder().screeningRPS("test").isScreeningSelected(true).shortlistingRPS("test").isShortlistingSelected(true)
				.qualAssessRPS("test").isQualAssessSelected(true).behavAssessRPS("test").isBehavAssessSelected(true).vidAssessRPS("test").isVidAssessSelected(true)
				.interview1RPS("test").interview2RPS("test").hrInterviewRPS("test").isHrInterviewSelected(true).isInterview1Selected(true).isInterview2Selected(true)
				.documentationRPS("test").isDocumentationSelected(true).offerRPS("test").isOfferSelected(true).build();
		
		jobDesc = JobDescription.builder().education("Degree").description("desc").rolesAndResponsibilty("roles")
				.primarySkills("primary").secondarySkills("secondary").additionalSkills("additional").build();
		
		requisition = Requisition.builder().band("5L").closingDate(new Date()).minExperience("2").maxExperience("4").grade("4")
				.isCampus(false).tatDays("5").startDate(new Date()).requisitionType("Normal").jobTitle("Test Engineer").hiringProcess(hiringprocess)
				.jobDescription(jobDesc).maxCtc("10L").minCtc("4L").client("Client").functionalArea("SET")
				.team("team").project("project").locationWiseOpeningsList(openingsList).build();
		
		dto = CandidateDTO.builder().addedByUsername("Foo").appliedFor("F1").contentType("F2").createdDate(new Date().toGMTString()).currentCTC("5L")
				.expectedCTC("8L").experience("5.5").fileURL("c://file.txt").firstName("Foo").lastName("Bar").location("loc").modifiedDate(new Date().toGMTString())
				.prefLocation("Foo").recommendation("rec").secondarySkills("ss").source("s12").summary("ss").requisition(requisition).build();
		
		candidate = CandidateModelMapper.mapToModel(dto);
		candidate.setCandidateId(1).setCreatedDate(new Date()).setModifiedDate(new Date());

	}

	@Test
	@DisplayName(value = "Get Candidate By Id Positive Scenario")
	void testGetCandidate_Positive() throws CandidateNotFoundException {
		ResponseEntity<CandidateDTO> expected = new ResponseEntity<CandidateDTO>(CandidateDTOMapper.mapToDTO(candidate),
				HttpStatus.OK);
		when(candidateService.findByCandidateId(any()))
				.thenReturn(new ResponseEntity<CandidateDTO>(CandidateDTOMapper.mapToDTO(candidate), HttpStatus.OK));
		ResponseEntity<CandidateDTO> result = candidateController.getCandidate(1);
		assertEquals(expected, result);
	}

	@Test
	@DisplayName(value = "Get Candidate By Id Negative Scenario")
	void testGetCandidate_Negative() throws CandidateNotFoundException {
		when(candidateService.findByCandidateId(any())).thenThrow(CandidateNotFoundException.class);
		assertThrows(CandidateNotFoundException.class, () -> candidateController.getCandidate(1));
	}

	@Test
	@DisplayName(value = "Get All Candidate By Username Positive Scenario")
	void testGetAllCandidatesByUsername_Positive() throws UsernameNotFoundException, CandidateNotFoundException {
		ResponseEntity<List<CandidateDTO>> expected = new ResponseEntity<List<CandidateDTO>>(HttpStatus.OK);
		when(candidateService.getAllCandidatesByUserName(any()))
				.thenReturn(new ResponseEntity<List<CandidateDTO>>(HttpStatus.OK));
		ResponseEntity<List<CandidateDTO>> result = candidateController.getAllCandidatesByUsername("pawan");
		assertEquals(expected, result);
	}

	@Test
	@DisplayName(value = "Get All Candidate By Username Negative Scenario")
	void testGetAllCandidatesByUsername_Negative() throws CandidateNotFoundException, UsernameNotFoundException {
		when(candidateService.getAllCandidatesByUserName(any())).thenThrow(UsernameNotFoundException.class);
		assertThrows(UsernameNotFoundException.class, () -> candidateController.getAllCandidatesByUsername("pawan"));
	}

	@Test
	@DisplayName(value = "Get All Candidates Positive Scenario")
	void testGetAllCandidates_Positive() throws UsernameNotFoundException, CandidateNotFoundException {
		ResponseEntity<List<CandidateDTO>> expected = new ResponseEntity<List<CandidateDTO>>(HttpStatus.OK);
		when(candidateService.getAllCandidates()).thenReturn(new ResponseEntity<List<CandidateDTO>>(HttpStatus.OK));
		ResponseEntity<List<CandidateDTO>> result = candidateController.getAllCandidates();
		assertEquals(expected, result);
	}

	@Test
	@DisplayName(value = "Delete Candidate By Id Positive Scenario")
	void testDeleteCandidateById_Positive() throws CandidateNotFoundException {
		ResponseEntity<String> expected = new ResponseEntity<String>(HttpStatus.OK);
		when(candidateService.deleteCandidateById(any())).thenReturn(expected);
		ResponseEntity<String> result = candidateController.deleteCandidate(1);
		assertEquals(expected, result);
	}

	@Test
	@DisplayName(value = "Delete Candidate By Id Negative Scenario")
	void testDeleteCandidateById_Negative() throws CandidateNotFoundException {
		when(candidateService.deleteCandidateById(any())).thenThrow(CandidateNotFoundException.class);
		assertThrows(CandidateNotFoundException.class, () -> candidateController.deleteCandidate(2));
	}

	@Test
	@DisplayName(value = "Update Candidate By Id Positive Scenario")
	void testUpdateCandidate_Positive() throws CandidateNotFoundException {
		ResponseEntity<CandidateDTO> expected = new ResponseEntity<CandidateDTO>(dto, HttpStatus.OK);
		when(candidateService.updateCandidate(eq(dto)))
				.thenReturn(new ResponseEntity<CandidateDTO>(dto, HttpStatus.OK));
		assertEquals(expected, candidateController.updateCandidate(dto));
	}

	@Test
	@DisplayName(value = "Update Candidate By Id Negative Scenario")
	void testUpdateCandidate_Negative() throws CandidateNotFoundException {
		when(candidateService.updateCandidate(eq(dto))).thenThrow(CandidateNotFoundException.class);
		assertThrows(CandidateNotFoundException.class, () -> candidateController.updateCandidate(dto));
	}

	@Test
	@DisplayName(value = "Create Candidate By Id Positive Scenario")
	void testCreateCandidate_Positive() throws CandidateNotFoundException {
		ResponseEntity<CandidateDTO> expected = new ResponseEntity<CandidateDTO>(CandidateDTOMapper.mapToDTO(candidate),
				HttpStatus.OK);
		when(candidateService.createCandidate(any()))
				.thenReturn(new ResponseEntity<CandidateDTO>(CandidateDTOMapper.mapToDTO(candidate), HttpStatus.OK));
		ResponseEntity<CandidateDTO> result = candidateController.createCandidate(dto);
		assertEquals(expected, result);
	}
}
