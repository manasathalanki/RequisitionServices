package com.wissen.recruit.requisition.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
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
import com.wissen.recruit.requisition.mapper.RequisitionMapper;
import com.wissen.recruit.requisition.repository.CandidateRepository;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CandidateServiceImplTest {

	@InjectMocks
	CandidateServiceImpl candidateServiceImpl;

	@Mock
	CandidateRepository candidateRepository;
	
	@Mock
	RequisitionService requisitionService;

	@Mock
	CandidateModelMapper candidateModelMapper;

	Candidate candidate;
	CandidateDTO dto;
	
	Requisition requisition;
	LocationWiseOpenings openings;
	HiringProcess hiringprocess;
	JobDescription jobDesc;
	
	List<LocationWiseOpenings> openingsList = new ArrayList<>();
	
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
				.jobDescription(jobDesc).maxCtc("10L").minCtc("4L").client("Client").functionalArea("SET").requisitionId(1).hiringManager("foo")
				.team("team").project("project").locationWiseOpeningsList(openingsList).build();
		
		dto = CandidateDTO.builder().candidateId(1).appliedFor("F1").contentType("F2").currentCTC("5L").expectedCTC("8L").experience("5.5")
				.fileURL("c://file.txt").firstName("Foo").lastName("Bar").location("loc").prefLocation("Foo").recommendation("rec")
				.secondarySkills("ss").source("s12").summary("ss").requisition(requisition).build();

		candidate = CandidateModelMapper.mapToModel(dto);
	}

	@Test
	@DisplayName(value = "Create Candidate Positive Scenario")
	void testCreateCandidate_Positive() {
		ResponseEntity<CandidateDTO> expected = new ResponseEntity<>(dto, HttpStatus.CREATED);
		when(candidateRepository.save(any())).thenReturn(candidate);
		assertEquals(expected, candidateServiceImpl.createCandidate(dto));
	}

	@Test
	@DisplayName(value = "Update Candidate Positive Scenario")
	void testUpdateCandidate_Positive() throws CandidateNotFoundException {
		dto.setAddedByUsername("abcde");
		when(candidateRepository.findById(any())).thenReturn(Optional.of(candidate));
		when(requisitionService.getRequisitionById(1)).thenReturn(RequisitionMapper.modeltoDTO(requisition));
		when(candidateRepository.save(any())).thenReturn(candidate);
		CandidateDTO actualResult = candidateServiceImpl.updateCandidate(dto).getBody();
		assertThat(actualResult).isNotNull();
	}

	@Test
	@DisplayName(value = "Update Candidate Negative Scenario")
	void testUpdateCandidate_Negative() throws CandidateNotFoundException {
		when(candidateRepository.findById(any())).thenReturn(Optional.empty());
		assertThrows(CandidateNotFoundException.class, () -> candidateServiceImpl.updateCandidate(dto));
	}

	@Test
	@DisplayName(value = "Get All Candidates Positive Scenario")
	void testGetAllCandidates_Positive() throws CandidateNotFoundException {

		List<Candidate> candidates = new ArrayList<>();
		candidates.add(candidate);

		List<CandidateDTO> responseDTO = candidates.stream().map(CandidateDTOMapper::mapToDTO).collect(Collectors.toList());

		ResponseEntity<List<CandidateDTO>> expected = new ResponseEntity<>(responseDTO, HttpStatus.OK);
		when(candidateRepository.findAll()).thenReturn(candidates);
		assertEquals(expected, candidateServiceImpl.getAllCandidates());
	}

	@Test
	@DisplayName(value = "Find By CandidateId Positive Scenario")
	void testFindByCandidateId_Positive() throws CandidateNotFoundException {
		ResponseEntity<CandidateDTO> expected = new ResponseEntity<>(dto, HttpStatus.OK);
		when(candidateRepository.findById(any())).thenReturn(Optional.of(candidate));
		assertEquals(expected, candidateServiceImpl.findByCandidateId(1));
	}

	@Test
	@DisplayName(value = "Find By CandidateId Negative Scenario")
	void testFindByCandidateId_Negative() throws CandidateNotFoundException {
		when(candidateRepository.findById(1)).thenReturn(Optional.empty());
		assertThrows(CandidateNotFoundException.class, () -> candidateServiceImpl.findByCandidateId(2));
	}

	@Test
	@DisplayName(value = "Delete Candidate By Id Positive Scenario")
	void testDeleteCandidateById_Positive() throws CandidateNotFoundException {
		ResponseEntity<String> expected = new ResponseEntity<>("Candidate Deleted", HttpStatus.OK);
		when(candidateRepository.findById(any())).thenReturn(Optional.of(candidate));
		doNothing().when(candidateRepository).delete(any());
		assertEquals(expected, candidateServiceImpl.deleteCandidateById(1));
	}

	@Test
	@DisplayName(value = "Delete Candidate By Id Negative Scenario")
	void testDeleteCandidateById_Negative() throws CandidateNotFoundException {
		when(candidateRepository.findById(1)).thenReturn(Optional.empty());
		assertThrows(CandidateNotFoundException.class, () -> candidateServiceImpl.deleteCandidateById(2));
	}

	@Test
	@DisplayName(value = "Validate Candidate Positive Scenario")
	void testValidateCandidate_Positive() throws CandidateNotFoundException {
		when(candidateRepository.findById(any())).thenReturn(Optional.of(candidate));
		assertEquals(candidate, candidateServiceImpl.validateCandidate(1));
	}

	@Test
	@DisplayName(value = "Validate Candidate Negative Scenario")
	void testValidateCandidate_Negative() throws CandidateNotFoundException {
		when(candidateRepository.findById(1)).thenReturn(Optional.empty());
		assertThrows(CandidateNotFoundException.class, () -> candidateServiceImpl.deleteCandidateById(2));
	}

	@Test
	@DisplayName(value = "Get All Candidates Positive Scenario")
	void testGetAllCandidatesByUserName_Positive() throws CandidateNotFoundException, UsernameNotFoundException {

		List<Candidate> candidates = new ArrayList<>();
		candidates.add(candidate);
		candidates.add(candidate);
		candidates.add(candidate);

		List<CandidateDTO> responseDTO = candidates.stream().map(CandidateDTOMapper::mapToDTO)
				.collect(Collectors.toList());

		ResponseEntity<List<CandidateDTO>> expected = new ResponseEntity<>(responseDTO, HttpStatus.OK);
		when(candidateRepository.findByAddedByUsername(any())).thenReturn(Optional.of(candidates));
		assertEquals(expected, candidateServiceImpl.getAllCandidatesByUserName("abcde"));
	}

	@Test
	@DisplayName(value = "Get All Candidates Negative Scenario for CandidateNotFoundException")
	void testGetAllCandidatesByUserName_Negative1() throws UsernameNotFoundException, CandidateNotFoundException {
		List<Candidate> candidates = new ArrayList<>();
		when(candidateRepository.findByAddedByUsername(any())).thenReturn(Optional.of(candidates));
		assertThrows(CandidateNotFoundException.class, () -> candidateServiceImpl.getAllCandidatesByUserName("abcde"));
	}
	
	@Test
	@DisplayName(value = "Get All Candidates Negative Scenario for UsernameNotFoundException")
	void testGetAllCandidatesByUserName_Negative2() throws UsernameNotFoundException, CandidateNotFoundException {
		when(candidateRepository.findByAddedByUsername(any())).thenReturn(Optional.empty());
		assertThrows(UsernameNotFoundException.class, () -> candidateServiceImpl.getAllCandidatesByUserName("abcde"));
	}

}
