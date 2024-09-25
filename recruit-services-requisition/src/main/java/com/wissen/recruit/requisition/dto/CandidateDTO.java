package com.wissen.recruit.requisition.dto;

import com.wissen.recruit.requisition.entity.Requisition;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain=true)
@Builder
@ApiModel(description="Sample Request Body of Candidate")
public class CandidateDTO {

	@ApiModelProperty(notes = "Candidate's Id")
	private Integer candidateId;
	
	@ApiModelProperty(notes = "Candidate's First Name")
	private String firstName;

	@ApiModelProperty(notes = "Candidate's Last Name")
	private String lastName;

	@ApiModelProperty(notes = "Candidate's Experience")
	private String experience;

	@ApiModelProperty(notes = "Candidate's Current CTC")
	private String currentCTC;

	@ApiModelProperty(notes = "Candidate's Expected CTC")
	private String expectedCTC;

	@ApiModelProperty(notes = "Candidate's Current Location")
	private String location;

	@ApiModelProperty(notes = "Candidate's Preferred Location")
	private String prefLocation;

	@ApiModelProperty(notes = "Candidate Applied For")
	private String appliedFor;

	@ApiModelProperty(notes = "Referral")
	private String recommendation;

	@ApiModelProperty(notes = "Primary skills")
	private String primarySkills;

	@ApiModelProperty(notes = "Secondary skills")
	private String secondarySkills;

	@ApiModelProperty(notes = "Candidate Creation Date")
	private String createdDate;
	
	@ApiModelProperty(notes = "Candidate Modified Date")
	private String modifiedDate;
	
	@ApiModelProperty(notes = "Resume or CV Document")
	private String fileURL;

	@ApiModelProperty(notes = "Resume or CV Document Type")
	private String contentType;

	@ApiModelProperty(notes = "Candidate Summary")
	private String summary;

	@ApiModelProperty(notes = "Source")
	private String source;	
	
	@ApiModelProperty(notes = "username which added this candidate")
	private String addedByUsername;
	
	@ApiModelProperty(notes = "applied to the requsition")
	private Requisition requisition;
}
