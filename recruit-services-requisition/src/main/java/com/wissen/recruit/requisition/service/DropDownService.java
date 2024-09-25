package com.wissen.recruit.requisition.service;

import java.util.List;

import com.wissen.recruit.requisition.entity.Band;
import com.wissen.recruit.requisition.entity.Designation;
import com.wissen.recruit.requisition.entity.Grade;
import com.wissen.recruit.requisition.entity.HiringManager;
import com.wissen.recruit.requisition.entity.InterviewPanel;
import com.wissen.recruit.requisition.entity.JobLocation;
import com.wissen.recruit.requisition.entity.Skills;

public interface DropDownService {

	HiringManager createHiringManager(HiringManager hiringManager);
	JobLocation createJobLocation(JobLocation jobLocation);
	Skills createSkills(Skills skills);
	InterviewPanel createInterviewPanel(InterviewPanel interviewPanel);
	Designation createDesignation(Designation designation);
	Grade createGrade(Grade grade);
	Band createBand(Band band);
	List<Designation> getAllDesignationDetails();
	List<Band> getAllBandDetails();
	List<Grade> getAllGradeDetails();
	List<HiringManager> getHiringManagerInfo();
	List<InterviewPanel> getpanelInfo();
	List<JobLocation> getAllLocations();
	List<Skills> getAllSkills();

}
