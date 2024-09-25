package com.wissen.recruit.requisition.dto;

import java.util.Date;
import java.util.List;

import com.wissen.recruit.requisition.entity.Candidate;
import com.wissen.recruit.requisition.entity.HiringProcess;
import com.wissen.recruit.requisition.entity.JobDescription;
import com.wissen.recruit.requisition.entity.LocationWiseOpenings;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "Sample Request Body of Requisition")
public class RequisitionDTO {
	
	@ApiModelProperty(notes = "The requisition Id ")
	private int requisitionId;

	@ApiModelProperty(notes = "Job Title")
	private String jobTitle;

	@ApiModelProperty(notes = "Band Details")
	private String band;

	@ApiModelProperty(notes = "Grade Details")
	private String grade;

	@ApiModelProperty(notes = "Minium Experience Required")
	private String minExperience;

	@ApiModelProperty(notes = "Maximum Experience Required")
	private String maxExperience;

	@ApiModelProperty(notes = "Recruit type - Campus/Non-Campus")
	private boolean isCampus;

	@ApiModelProperty(notes = "Requisition Type")
	private String requisitionType;

	@ApiModelProperty(notes = "Tat Days")
	private String tatDays;
	
	@ApiModelProperty(notes = "Tat Hours")
	private String tatHours;

	@ApiModelProperty(notes = "Hiring Manager Name")
	private String hiringManager;

	@ApiModelProperty(notes = "requisition start date")
	private Date startDate;

	@ApiModelProperty(notes = "requisition close date")
	private Date closingDate;

	@ApiModelProperty(notes = "Minimum CTC")
	private String minCtc;

	@ApiModelProperty(notes = "Maximum CTC")
	private String maxCtc;

	@ApiModelProperty(notes = "Openings based on location")
	private List<LocationWiseOpenings> locationWiseOpeningsList;

	@ApiModelProperty(notes = "Shift Timing")
	private String shiftTiming;

	@ApiModelProperty(notes = "Functional Area")
	private String functionalArea;

	@ApiModelProperty(notes = "Team")
	private String team;

	@ApiModelProperty(notes = "Client")
	private String client;

	@ApiModelProperty(notes = "Project")
	private String project;

	@ApiModelProperty(notes = "Hiring Steps")
	private HiringProcess hiringProcess;

	@ApiModelProperty(notes = "Job Description")
	private JobDescription jobDescription;

	@ApiModelProperty(notes = "Requisition Created Date")
	private Date createdDate;

	@ApiModelProperty(notes = "Requisition Modified Date")
	private Date modifiedDate;

	@ApiModelProperty(notes = "Applied Candidates")
	private List<Candidate> candidateList;

}
