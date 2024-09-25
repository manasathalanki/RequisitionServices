package com.wissen.recruit.requisition.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wissen.recruit.requisition.entity.Band;
import com.wissen.recruit.requisition.entity.Designation;
import com.wissen.recruit.requisition.entity.Grade;
import com.wissen.recruit.requisition.entity.HiringManager;
import com.wissen.recruit.requisition.entity.InterviewPanel;
import com.wissen.recruit.requisition.entity.JobLocation;
import com.wissen.recruit.requisition.entity.Skills;
import com.wissen.recruit.requisition.service.DropDownService;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@RestController
@RequestMapping("/requisition/dropdown")
@EnableSwagger2
public class DropDownController {

	Logger logger = LoggerFactory.getLogger(RequisitionController.class);
	
	@Autowired
	DropDownService service;
	
	@GetMapping("/skill")
	@ApiOperation(value = "getAllSkillsDropdown", notes="Get All Skills For Dropdown",nickname = "getAllSkillsDropdown")
	public ResponseEntity<List<Skills>> getSkillsDropdown(){
		logger.info("requsiontion controller method - getAllSkillsForDropdown called... {}", "");
		List<Skills> skillList = service.getAllSkills();
		
		logger.info("requsiontion controller - Fetched all skills ... {}", "");
		return new ResponseEntity<>(skillList, HttpStatus.OK);
	}
	
	@GetMapping("/joblocation")
	@ApiOperation(value = "getJobLocationsDropdown", notes="Get Job Locations For Dropdown",nickname = "getJobLocationsDropdown")
	public ResponseEntity<List<JobLocation>> getJobLocationsDropdown(){
		logger.info("requsiontion controller method - getJobLocationsDropdown called... {}", "");
		List<JobLocation> locationList = service.getAllLocations();
		
		logger.info("requsiontion controller - Fetched all job locations ... {}", "");
		return new ResponseEntity<>(locationList, HttpStatus.OK);
	}
	
	@GetMapping("/interviewpanel")
	@ApiOperation(value = "getInterviewPanelDropdown", notes="get Interview Panel For Dropdown",nickname = "getInterviewPanelDropdown")
	public ResponseEntity<List<InterviewPanel>> getInterviewPanelDropdown(){
		logger.info("requsiontion controller method - getInterviewPanelDropdown called... {}", "");
		List<InterviewPanel> panelList = service.getpanelInfo();
		
		logger.info("requsiontion controller - Fetched all interview panel details ... {}", "");
		return new ResponseEntity<>(panelList, HttpStatus.OK);
	}
	
	@GetMapping("/hiringmanager")
	@ApiOperation(value = "getHiringManagerDropdown", notes="Get Hiring Manager For Dropdown",nickname = "getHiringManagerDropdown")
	public ResponseEntity<List<HiringManager>> getHiringManagerDropdown(){
		logger.info("requsiontion controller method - getInterviewPanelDropdown called... {}", "");
		List<HiringManager> hiringManagerList = service.getHiringManagerInfo();
		
		logger.info("requsiontion controller - Fetched all interview panel details ... {}", "");
		return new ResponseEntity<>(hiringManagerList, HttpStatus.OK);
	}
	
	@GetMapping("/grade")
	@ApiOperation(value = "getAllGradeDetails", notes = "Get all Grade Details", nickname = "getAllGradeDetails")
	public ResponseEntity<List<Grade>> getAllGradeDetails() {
		logger.info("requsiontion controller method - getAllGradeDetails called... {}", "");
		List<Grade> gradesList = service.getAllGradeDetails();
		
		logger.info("requsiontion controller - Fetched all the grade details ... {}", "");
		return new ResponseEntity<>(gradesList, HttpStatus.OK);
	}
	
	@GetMapping("/band")
	@ApiOperation(value = "getAllBandDetails", notes = "Get all Band Details", nickname = "getAllBandDetails")
	public ResponseEntity<List<Band>> getAllBandDetails() {
		logger.info("requsiontion controller method - getAllBandDetails called... {}", "");
		List<Band> bandList = service.getAllBandDetails();
		
		logger.info("requsiontion controller - Fetched all the band requisitions ... {}", "");
		return new ResponseEntity<>(bandList, HttpStatus.OK);
	}
	
	@GetMapping("/designation")
	@ApiOperation(value = "getAlldesignationDetails", notes = "Get all Designation Details", nickname = "getAllDesignationDetails")
	public ResponseEntity<List<Designation>> getAllDesignationDetails() {
		logger.info("requsiontion controller method - getAllDesignationDetails called... {}", "");
		List<Designation> designationList = service.getAllDesignationDetails();
	
		logger.info("requsiontion controller - Fetched all the designation requisitions ... {}", "");
		return new ResponseEntity<>(designationList, HttpStatus.OK);
	}
	
	@PostMapping("/band")
	@ApiOperation(value = "createBand", notes = "Create new Band", nickname = "createBand")
	public ResponseEntity<Band> createBand(@Valid @RequestBody Band band) {
		logger.info("requisiontion controller - createBand method called... {}", band.getValue());
		Band savedband = service.createBand(band);
		
		logger.info("Band saved with id ... {}", savedband.getBandId());
		return new ResponseEntity<>(savedband, HttpStatus.CREATED);
	}
	
	@PostMapping("/grade")
	@ApiOperation(value = "createGrade", notes = "Create new Grade", nickname = "createGrade")
	public ResponseEntity<Grade> createGrade(@Valid @RequestBody Grade grade) {
		logger.info("requisiontion controller - createGrade method called... {}", grade.getValue());
		Grade savedGrade = service.createGrade(grade);
		
		logger.info("Grade saved with id ... {}", savedGrade.getGradeId());
		return new ResponseEntity<>(savedGrade, HttpStatus.CREATED);
	}

	@PostMapping("/designation")
	@ApiOperation(value = "createDesignation", notes = "Create new Designation", nickname = "createDesignation")
	public ResponseEntity<Designation> createDesignation(@Valid @RequestBody Designation designation) {
		logger.info("requisiontion controller - createDesignation method called... {}", designation.getValue());
		Designation savedDesignation = service.createDesignation(designation);
		
		logger.info("designation saved with id ... {}", savedDesignation.getDesignationId());
		return new ResponseEntity<>(savedDesignation, HttpStatus.CREATED);
	}

	@PostMapping("/interviewpanel")
	@ApiOperation(value = "createInterviewPanel", notes = "Create new InterviewPanel", nickname = "createInterviewPanel")
	public ResponseEntity<InterviewPanel> createInterviewPanel(@Valid @RequestBody InterviewPanel interviewPanel) {
		logger.info("requisiontion controller - createInterviewPanel method called... {}", interviewPanel.getValue());
		InterviewPanel savedpanel = service.createInterviewPanel(interviewPanel);
		
		logger.info("requsiontion created with id ... {}", savedpanel.getId());
		return new ResponseEntity<>(savedpanel, HttpStatus.CREATED);
	}

	@PostMapping("/hiringmanager")
	@ApiOperation(value = "createHiringManager", notes = "Create new HiringManager", nickname = "createHiringManager")
	public ResponseEntity<HiringManager> createHiringManager(@Valid @RequestBody HiringManager hiringManager) {
		logger.info("requisiontion controller - createHiringManager method called... {}", hiringManager.getValue());
		HiringManager savedHiringManager = service.createHiringManager(hiringManager);
		
		logger.info("Hiring Manager deatils saved with id ... {}", savedHiringManager.getId());
		return new ResponseEntity<>(savedHiringManager, HttpStatus.CREATED);
	}

	@PostMapping("/joblocation")
	@ApiOperation(value = "createJobLocation", notes = "Create new JobLocation", nickname = "createJobLocation")
	public ResponseEntity<JobLocation> createJobLocation(@Valid @RequestBody JobLocation jobLocation) {
		logger.info("requisiontion controller - createJobLocation method called... {}", jobLocation.getValue());
		JobLocation savedJobLocation = service.createJobLocation(jobLocation);
		
		logger.info("Job Location saved with id ... {}", savedJobLocation.getId());
		return new ResponseEntity<>(savedJobLocation, HttpStatus.CREATED);
	}

	@PostMapping("/skill")
	@ApiOperation(value = "createSkills", notes = "Create new Skills", nickname = "createSkills")
	public ResponseEntity<Skills> createSkills(@Valid @RequestBody Skills skills) {
		logger.info("requisiontion controller - createSkills method called... {}", skills.getValue());
		Skills savedSkills = service.createSkills(skills);
		
		logger.info("Skill saved with id ... {}", savedSkills.getId());
		return new ResponseEntity<>(savedSkills, HttpStatus.CREATED);
	}

}
