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

import com.wissen.recruit.requisition.dto.RequisitionDTO;
import com.wissen.recruit.requisition.entity.HiringProcess;
import com.wissen.recruit.requisition.entity.JobDescription;
import com.wissen.recruit.requisition.entity.LocationWiseOpenings;
import com.wissen.recruit.requisition.entity.Requisition;
import com.wissen.recruit.requisition.exception.RequisitionNotFoundException;
import com.wissen.recruit.requisition.mapper.RequisitionMapper;
import com.wissen.recruit.requisition.repository.RequisitionRepository;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
class RequisitionServiceImplTest {

	@Mock
	private RequisitionRepository requisitionRepository;
	
	@InjectMocks
	private RequisitionServiceImpl requisitionService;
	
	Requisition details, expectedResults;
	HiringProcess hiringprocess;
	JobDescription jobDesc;
	
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
				.team("team").project("project").locationWiseOpeningsList(openingsList).build();
		
		requisitionsList.add(details);
	}
	
	@Test
	void test_createRequisition_Success_WithAllChildEntities() {
		expectedResults = Requisition.builder().band("5L").closingDate(new Date()).minExperience("2").maxExperience("4").grade("4")
				.isCampus(false).tatDays("5").startDate(new Date()).requisitionType("Normal").jobTitle("Test Engineer").hiringProcess(hiringprocess)
				.jobDescription(jobDesc).maxCtc("10L").minCtc("4L").client("Client").functionalArea("SET")
				.team("team").project("project").locationWiseOpeningsList(openingsList).build();
			
		when(requisitionRepository.save(details)).thenReturn(expectedResults);
		RequisitionDTO actualResult = requisitionService.createRequisition(RequisitionMapper.modeltoDTO(details));
		assertThat(actualResult.getJobTitle()).isEqualTo("Test Engineer");
		assertThat(actualResult.getRequisitionId()).isNotNegative();
		assertThat(actualResult.getHiringProcess().getHiringProcessId()).isNotNegative();
		assertThat(actualResult.getLocationWiseOpeningsList().get(0).getLocation()).isEqualTo("Hyd");
	}
	
	@Test
	void test_createRequisition_Success_WithoutChildEntities() {
		details.setHiringProcess(null);
		details.setJobDescription(null);
		details.setLocationWiseOpeningsList(null);
		expectedResults = Requisition.builder().requisitionId(1).band("5L").closingDate(new Date()).minExperience("2").maxExperience("4").grade("4")
				.isCampus(false).tatDays("5").startDate(new Date()).requisitionType("Normal").jobTitle("Test Engineer").hiringProcess(null)
				.jobDescription(null).maxCtc("10L").minCtc("4L").client("Client").functionalArea("SET")
				.team("team").hiringManager("foo").project("project").locationWiseOpeningsList(null).build();
			
		when(requisitionRepository.save(details)).thenReturn(expectedResults);
		RequisitionDTO actualResult = requisitionService.createRequisition(RequisitionMapper.modeltoDTO(details));
		assertThat(actualResult.getRequisitionId()).isNotNegative();
		assertThat(actualResult.getHiringProcess()).isNull();
		assertThat(actualResult.getJobTitle()).isEqualTo("Test Engineer");
		assertThat(actualResult.getJobDescription()).isNull();
		assertThat(actualResult.getLocationWiseOpeningsList()).isNull();
	}
	
	@Test
	void test_getAllRequisitions() {
		when(requisitionRepository.findAll()).thenReturn(requisitionsList);
		List<RequisitionDTO> actualResult = requisitionService.getAllRequisitions();
		
		assertThat(actualResult).isNotNull()
								.hasSize(1);
	}
	
	@Test
	void test_getAllRequisition_returnsEmptyList() {
		when(requisitionRepository.findAll()).thenReturn(new ArrayList<>());
		List<RequisitionDTO> expectedResult = requisitionService.getAllRequisitions();
		
		assertThat(expectedResult).isEmpty();
	}
	
	@Test
	void test_getRequisitionById_Success() {
		hiringprocess.setHiringProcessId(1);
		when(requisitionRepository.findById(1)).thenReturn(Optional.of(details));
		 RequisitionDTO expectedResult = requisitionService.getRequisitionById(1);
		 
		 assertThat(expectedResult).isNotNull();
		 assertThat(expectedResult.getJobTitle()).isEqualTo("Test Engineer");
		 assertThat(expectedResult.getHiringProcess().getHiringProcessId()).isEqualTo(1);
		 assertThat(expectedResult.getJobDescription().getDescription()).isEqualTo("desc");
		 assertThat(expectedResult.getLocationWiseOpeningsList()).hasSize(2);
	}
	
	@Test
	void test_getRequisitionById_RequisitionDetailsNotFoundException() {
		Throwable exception = assertThrows(
				 RequisitionNotFoundException.class, () -> {
					 requisitionService.getRequisitionById(1);
		            }
		    );
		assertEquals("Requisition details not found for the id 1", exception.getMessage());
	}
	
	@Test
	void test_getRequisitionById_Thorw_RequisitionDetailsNotFoundException() {
		when(requisitionRepository.findById(111111)).thenThrow(RequisitionNotFoundException.class);
		assertThrows(RequisitionNotFoundException.class, () -> {requisitionService.getRequisitionById(111111); });
	}
	
	@Test
	void test_DeleteRequisitionById_Success() {
		hiringprocess.setHiringProcessId(1);
		when(requisitionRepository.findById(1)).thenReturn(Optional.of(details));
		doNothing().when(requisitionRepository).deleteById(1);
		
		 RequisitionDTO expectedResult = requisitionService.deleteRequisitionById(1);
		 verify(requisitionRepository, times(1)).deleteById(1);
		 verifyNoMoreInteractions(requisitionRepository);
		 
		 assertThat(expectedResult).isNotNull();
		 assertThat(expectedResult.getJobTitle()).isEqualTo("Test Engineer");
		 assertThat(expectedResult.getHiringProcess().getHiringProcessId()).isEqualTo(1);
	}
	
	@Test
	void test_DeleteRequisitionById_Thorw_RequisitionNotFoundException() {	
		 Throwable exception = assertThrows(
				 RequisitionNotFoundException.class, () -> {
					 requisitionService.deleteRequisitionById(0);
		            }
		    );
		 assertEquals("Requisition details not found for the id 0", exception.getMessage());
	}
	
	@Test
	void test_UpdateRequisition_Success_WithChildEntities() {
		hiringprocess.setHiringProcessId(1);
		jobDesc.setJobDescId(1);
		when(requisitionRepository.findById(1)).thenReturn(Optional.of(details));
		
		details.setRequisitionId(1);
		details.setJobTitle("Title");
		details.setBand("Band");
		//hiringprocess.setScreeningPerson("Person");
		jobDesc.setDescription("New");
		details.setMaxCtc("12L");
		details.setClient("New Client");
		jobDesc.setAdditionalSkills("New Skill");
		
		when(requisitionRepository.save(details)).thenReturn(details);
		
		RequisitionDTO actualResult = requisitionService.updateRequisition(RequisitionMapper.modeltoDTO(details));
		
		assertThat(actualResult).isNotNull();
		assertThat(actualResult.getJobTitle()).isEqualTo("Title");
		assertThat(actualResult.getBand()).isEqualTo("Band");
		//assertThat(actualResult.getHiringProcess().getScreeningPerson()).isEqualTo("Person");
		assertThat(actualResult.getJobDescription().getDescription()).isEqualTo("New");
		assertThat(actualResult.getMaxCtc()).isEqualTo("12L");
		assertThat(actualResult.getClient()).isEqualTo("New Client");
		assertThat(actualResult.getJobDescription().getAdditionalSkills()).isEqualTo("New Skill");
	}
	
	@Test
	void test_UpdateRequisition_Throw_Exception() {
		
		when(requisitionRepository.findById(1)).thenReturn(Optional.empty());
		
		details.setRequisitionId(1);
		details.setJobTitle("Title");
		details.setBand("Band");
		//hiringprocess.setScreeningPerson("Person");
		jobDesc.setDescription("New");
		details.setMaxCtc("12L");
		details.setClient("New Client");
		jobDesc.setAdditionalSkills("New Skill");
		
		assertThrows(RequisitionNotFoundException.class, () -> requisitionService.updateRequisition(RequisitionMapper.modeltoDTO(details)));
		
		
		
	}
	
	@Test
	void test_UpdateRequisition_Success_WithoutChildEntities() {
		
		details.setRequisitionId(1);
		details.setJobTitle("Title");
		details.setBand("Band");
		details.setHiringProcess(null);
		details.setJobDescription(null);
		details.setLocationWiseOpeningsList(null);
		
		when(requisitionRepository.findById(1)).thenReturn(Optional.of(details));
		
		details.setJobDescription(null);
		details.setLocationWiseOpeningsList(null);
		details.setHiringProcess(null);
		
		when(requisitionRepository.save(details)).thenReturn(details);
		
		RequisitionDTO actualResult = requisitionService.updateRequisition(RequisitionMapper.modeltoDTO(details));
		
		assertThat(actualResult).isNotNull();
		assertThat(actualResult.getJobTitle()).isEqualTo("Title");
		assertThat(actualResult.getBand()).isEqualTo("Band");
	}
	@Test
	void test_UpdateRequisition_Thorw_RequisitionNotFoundException() throws RequisitionNotFoundException {
		details.setRequisitionId(0);
		when(requisitionRepository.findById(0)).thenThrow(RequisitionNotFoundException.class);
		assertThrows(RequisitionNotFoundException.class, () -> {
			 requisitionService.updateRequisition(RequisitionMapper.modeltoDTO(details));
		 });
		verify(requisitionRepository, never()).save(details);
	}
}
