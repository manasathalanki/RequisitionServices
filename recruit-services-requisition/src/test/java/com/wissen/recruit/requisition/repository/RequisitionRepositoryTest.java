package com.wissen.recruit.requisition.repository;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.Date;
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

import com.wissen.recruit.requisition.entity.HiringProcess;
import com.wissen.recruit.requisition.entity.JobDescription;
import com.wissen.recruit.requisition.entity.LocationWiseOpenings;
import com.wissen.recruit.requisition.entity.Requisition;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DataJpaTest
class RequisitionRepositoryTest {

	@Autowired
	private RequisitionRepository requisitionRepository;

	Requisition details;
	HiringProcess hiringprocess;
	JobDescription jobDesc;
	LocationWiseOpenings openings;
	
	List<Requisition> requisitionsList = new ArrayList<>();
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
		
		requisitionRepository.save(details);
	}

	@AfterEach
	void destroyAll() {
		requisitionRepository.deleteAll();
	}

	@Test
	void test_createRequisition_WithAllChildEntities() {
		Requisition actualResult = requisitionRepository.save(details);

		assertThat(actualResult).isNotNull();
		assertThat(actualResult.getRequisitionId()).isPositive();
		assertThat(actualResult.getHiringProcess().getHiringProcessId()).isPositive();  //Hiring process check
		assertThat(actualResult.getJobTitle()).isEqualTo("Test Engineer");
		assertThat(actualResult.getJobDescription().getJobDescId()).isPositive();  //Job description check
		assertThat(actualResult.getLocationWiseOpeningsList()).isNotEmpty();
	}

	@Test
	void test_createRequisition_WithoutChildEntities() {
		details.setHiringProcess(null);
		details.setJobDescription(null);
		details.setLocationWiseOpeningsList(null);
		
		Requisition actualResult = requisitionRepository.save(details);

		assertThat(actualResult).isNotNull();
		assertThat(actualResult.getRequisitionId()).isPositive();
		assertThat(actualResult.getHiringProcess()).isNull();
		assertThat(actualResult.getJobTitle()).isEqualTo("Test Engineer");
		assertThat(actualResult.getJobDescription()).isNull();
	}

	@Test
	void test_getAllRequisitions_success() {
		List<Requisition> actualResult = requisitionRepository.findAll();
		assertThat(actualResult).isNotNull().hasSize(1);
		assertThat(actualResult.get(0).getHiringProcess()).isNotNull();
		assertThat(actualResult.get(0).getJobDescription()).isNotNull();
		assertThat(actualResult.get(0).getLocationWiseOpeningsList()).isNotNull();
	}

	@Test
	void test_getAllRequisitions_Fail() {
		requisitionRepository.deleteAll();
		List<Requisition> actualResult = requisitionRepository.findAll();
		assertThat(actualResult).isNotNull().isEmpty();
	}

	@Test
	void test_getRequisitionById_Success() {
		Requisition requisition = requisitionRepository.save(details);
		Optional<Requisition> actualResult = requisitionRepository.findById(requisition.getRequisitionId());
		assertThat(actualResult).isNotNull().isNotEmpty();
		assertThat(actualResult.get().getJobTitle()).isEqualTo("Test Engineer");
		//assertThat(actualResult.get().getHiringProcess().getScreeningPerson()).isEqualTo("Foo");
		assertThat(actualResult.get().getJobDescription().getDescription()).isEqualTo("desc");
		assertThat(actualResult.get().getJobDescription().getPrimarySkills()).isEqualTo("primary");
		assertThat(actualResult.get().getMaxCtc()).isEqualTo("10L");
		
	}

	@Test
	void test_getRequisitionById_Failure() {
		Optional<Requisition> actualResult = requisitionRepository.findById(123456);
		assertThat(actualResult).isEmpty().isNotNull();
	}

	@Test
	void test_deleteRequisitionById_Success() {
		Requisition requisition = requisitionRepository.save(details);

		requisitionRepository.deleteById(requisition.getRequisitionId());
		assertThat(requisitionRepository.findAll()).isEmpty();
	}

	@Test
	void test_UpdateRequisition_Success_WithAllChildEntities() {
		Requisition req = requisitionRepository.save(details);

		Requisition savedrequisition = requisitionRepository.findById(req.getRequisitionId()).get();
		savedrequisition.setJobTitle("Job");

		//hiringprocess.setScreeningPerson("Person");

		Requisition updatedrequisition = requisitionRepository.save(savedrequisition);

		assertThat(updatedrequisition.getRequisitionId()).isEqualTo(savedrequisition.getRequisitionId());
		assertThat(updatedrequisition.getJobTitle()).isEqualTo("Job");
		//assertThat(updatedrequisition.getHiringProcess().getScreeningPerson()).isEqualTo("Person");
		assertThat(updatedrequisition.getJobDescription().getJobDescId()).isEqualTo(savedrequisition.getJobDescription().getJobDescId());
	}

}
