package com.wissen.recruit.requisition.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.wissen.recruit.requisition.entity.Candidate;
import com.wissen.recruit.requisition.entity.HiringProcess;
import com.wissen.recruit.requisition.entity.JobDescription;
import com.wissen.recruit.requisition.entity.LocationWiseOpenings;
import com.wissen.recruit.requisition.entity.Requisition;

@SpringBootTest
@ActiveProfiles(value = "qa")
class CandidateRepositoryTest {

	@Autowired
	CandidateRepository candidateRepository;

	@Autowired
	RequisitionRepository requisitionRepository;
	
	Candidate candidate;
	Requisition requisition;
	LocationWiseOpenings openings;
	HiringProcess hiringprocess;
	JobDescription jobDesc;
	
	List<LocationWiseOpenings> openingsList = new ArrayList<>();

	@BeforeEach
	void setup() {
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
				.team("team").project("project").hiringManager("foo").locationWiseOpeningsList(openingsList).build();
		
		candidate = Candidate.builder().candidateId(1).addedByUsername("Foo").appliedFor("F1").contentType("F2").createdDate(new Date()).currentCTC("5L")
				.expectedCTC("8L").experience("5.5").fileUrl("c://file.txt").firstName("Foo").lastName("Bar").location("loc").modifiedDate(new Date())
				.prefLocation("Foo").recommendation("rec").secondarySkills("ss").source("s12").summary("ss").requisition(requisition).build();
		
		requisitionRepository.save(requisition);
		candidateRepository.save(candidate);
	}

	@AfterEach
	void destroyAll() {
		candidateRepository.deleteAll();
	}

	@Test
	@DisplayName(value = "Positive Test for CreateCandidate Method in Candidate Repo")
	void createCandidate() {
		Candidate actualResult = candidateRepository.save(candidate);
		assertThat(actualResult).isNotNull();
		assertThat(actualResult.getAddedByUsername()).isEqualTo("Foo");

	}
	
	@Test
	@DisplayName(value = "Positive Test for updateCandidate Method in Candidate Repo")
	void updateCandidate() {
		Candidate can = candidateRepository.save(candidate);
		Candidate savedCandidate = candidateRepository.findById(can.getCandidateId()).get();
		savedCandidate.setAddedByUsername("User");
		savedCandidate.setExperience("3");
		Candidate updatedCandidate = candidateRepository.save(savedCandidate);
		assertThat(updatedCandidate.getCandidateId()).isEqualTo(savedCandidate.getCandidateId());
		assertThat(updatedCandidate.getAddedByUsername()).isEqualTo("User");
		assertThat(updatedCandidate.getExperience()).isEqualTo("3");

	}

	@Test
	@DisplayName(value = "Positive Test for getAllCandidates Method in Candidate Repo")
	void testGetAllCandidates_Positive() {

		List<Candidate> actualResult = candidateRepository.findAll();
		assertThat(actualResult).isNotNull().hasSize(1);
	}

	@Test
	@DisplayName(value = "Negative Test for getAllCandidates Method in Candidate Repo")
	void testGetAllCandidates_Negative() {
		candidateRepository.deleteAll();
		List<Candidate> actualResult = candidateRepository.findAll();
		assertThat(actualResult).isEmpty();
	}

	@Test
	@DisplayName(value = "Positive Test for getCandidateById Method in Candidate Repo")
	void testFindByCandidateId_Positive() {
		Candidate can = candidateRepository.save(candidate);
		Optional<Candidate> actualResult = candidateRepository.findById(can.getCandidateId());
		assertThat(actualResult).isNotNull().isNotEmpty();
		assertThat(actualResult.get().getCurrentCTC()).isEqualTo("5L");
		assertThat(actualResult.get().getFileUrl()).isEqualTo("c://file.txt");

	}

	@Test
	@DisplayName(value = "Negative Test for getCandidateById Method in Candidate Repo")
	void testFindByCandidateId_Negative() {

		Optional<Candidate> actualResult = candidateRepository.findById(15);
		assertThat(actualResult).isNotNull().isEmpty();

	}
	
	@Test
	@DisplayName(value = "Positive Test for FindByUsername Method in Candidate Repo")
	void testFindByUsername_Positive() {
		Optional<List<Candidate>> foundCandidate = candidateRepository.findByAddedByUsername("Foo");
		assertThat(foundCandidate).isPresent();
	}

	@Test
	@DisplayName(value = "Negative Test for FindByUsername Method in Candidate Repo")
	void testFindByUsername_Negative() {
		Optional<List<Candidate>> foundCandidate = candidateRepository.findByAddedByUsername("vamsi@wissen");
		assertEquals(0, foundCandidate.get().size());
	}
}
