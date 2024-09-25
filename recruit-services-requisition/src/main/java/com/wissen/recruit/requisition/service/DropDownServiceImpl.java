package com.wissen.recruit.requisition.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wissen.recruit.requisition.entity.Band;
import com.wissen.recruit.requisition.entity.Designation;
import com.wissen.recruit.requisition.entity.Grade;
import com.wissen.recruit.requisition.entity.HiringManager;
import com.wissen.recruit.requisition.entity.InterviewPanel;
import com.wissen.recruit.requisition.entity.JobLocation;
import com.wissen.recruit.requisition.entity.Skills;
import com.wissen.recruit.requisition.repository.BandRepository;
import com.wissen.recruit.requisition.repository.DesignationRepository;
import com.wissen.recruit.requisition.repository.GradeRepository;
import com.wissen.recruit.requisition.repository.HiringManagerRepository;
import com.wissen.recruit.requisition.repository.InterviewPanelRepository;
import com.wissen.recruit.requisition.repository.JobLocationRepository;
import com.wissen.recruit.requisition.repository.SkillRepository;

@Service
public class DropDownServiceImpl implements DropDownService {

	final Logger logger = LoggerFactory.getLogger(RequisitionServiceImpl.class);
	
	@Autowired
	SkillRepository skillRepository;
	
	@Autowired
	HiringManagerRepository hiringManagerRepository;
	
	@Autowired
	InterviewPanelRepository interviewPanelRepository;
	
	@Autowired
	JobLocationRepository jobLocationRepository;
	
	@Autowired
	GradeRepository gradeRepository;
	
	@Autowired
	BandRepository bandRepository;
	
	@Autowired
	DesignationRepository designationRepository;
	
	@Override
	public List<Skills> getAllSkills() {
		return skillRepository.findAll();
	}

	@Override
	public List<JobLocation> getAllLocations() {
		return jobLocationRepository.findAll();
	}

	@Override
	public List<InterviewPanel> getpanelInfo() {
		return interviewPanelRepository.findAll();
	}

	@Override
	public List<HiringManager> getHiringManagerInfo() {
		return hiringManagerRepository.findAll();
	}
	
	@Override
	public List<Grade> getAllGradeDetails() {
		logger.info("requsiontion service method - getAllGradeDetails called... {}", "");
		List<Grade> gradesList = gradeRepository.findAll();
		logger.info("requsiontion service - Fetched all the  grade details ... {}", "");
		return gradesList;
	}
	
	@Override
	public List<Designation> getAllDesignationDetails() {
		logger.info("requsiontion service method - getAlldesignationDetails called... {}", "");
		List<Designation> designationList = designationRepository.findAll();
		
		 logger.info("requsiontion service - Fetched all the designation details ... {}", "");
		 return designationList;
	}

	@Override
	public List<Band> getAllBandDetails() {
		logger.info("requsiontion service method - getAllBandDetails called... {}", "");
		List<Band> bandList = bandRepository.findAll();
		logger.info("requsiontion service - Fetched all the band details ... {}", "");
		return bandList;
	}

	@Override
	public Band createBand(Band band) {
		logger.info("requsiontion service method - createBand called... {}", band.getValue());
		Band savedBand = bandRepository.save(band);
		
		logger.info("requsiontion service - Band saved ... {}", savedBand.getBandId());
		return savedBand;
	}

	@Override
	public Designation createDesignation(Designation designation) {
		logger.info("requsiontion service method - createDesignation called... {}", designation.getValue());
		Designation savedDesignation = designationRepository.save(designation);
		
		logger.info("requsiontion service - Designation saved ... {}", savedDesignation.getDesignationId());
		return savedDesignation;
	}

	@Override
	public Grade createGrade(Grade grade) {
		logger.info("requsiontion service method - createGrade called... {}", grade.getValue());
		Grade savedGrade = gradeRepository.save(grade);
		
		logger.info("requsiontion service - Grade saved ... {}", savedGrade.getGradeId());
		return savedGrade;
	}

	@Override
	public Skills createSkills(Skills skills) {
		logger.info("create requsiontion service method - createSkills called... {}", skills.getValue());
		Skills savedSkills = skillRepository.save(skills);

		logger.info("requsiontion service - Skill saved ... {}", savedSkills.getId());
		return savedSkills;
	}

	@Override
	public JobLocation createJobLocation(JobLocation joblocation) {
		logger.info("create requsiontion service method - createJobLocation called... {}", joblocation.getValue());
		JobLocation savedJobLocation = jobLocationRepository.save(joblocation);
		
		logger.info("requsiontion service - Job Location saved ... {}", savedJobLocation.getId());
		return savedJobLocation;
	}

	@Override
	public InterviewPanel createInterviewPanel(InterviewPanel interviewPanel) {
		logger.info("create requsiontion service method - createInterviewPanel called... {}", interviewPanel.getValue());
		InterviewPanel savedInterviewPanel = interviewPanelRepository.save(interviewPanel);

		logger.info("requsiontion service - Interview Panel Details saved ... {}", savedInterviewPanel.getId());
		return savedInterviewPanel;
	}

	@Override
	public HiringManager createHiringManager(HiringManager hiringManager) {
		logger.info("requsiontion service method - createHiringManager called... {}", hiringManager.getValue());
		HiringManager savedHiringManager = hiringManagerRepository.save(hiringManager);
		
		logger.info("requsiontion service - Hiring Manager Details saved ... {}", savedHiringManager.getId());
		return savedHiringManager;
	}

}
