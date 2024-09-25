package com.wissen.recruit.requisition.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Entity
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class HiringProcess implements Serializable{

	private static final long serialVersionUID = -5217219250702206986L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "hiringprocess_id")
	@ApiModelProperty(notes = "Hiring Process Id")
	private int hiringProcessId;
	
	@ApiModelProperty(notes = "Screening process")
	private boolean isScreeningSelected;
	
	@ApiModelProperty(notes = "Screening process Assignee Details - Role, Person, SLA")
	private String screeningRPS; // Role Person SLA //expected obj from UI request [{Role: "", Person: "", SLA: ""}, {Role: "", Person: "", SLA: ""}]
	
	@ApiModelProperty(notes = "Shortlisting process")
	private boolean isShortlistingSelected;
	
	@ApiModelProperty(notes = "Shortlisting process Assignee Details - Role, Person, SLA")
	private String shortlistingRPS;
	
	@ApiModelProperty(notes = "Quantitative Assessment process")
	private boolean isQualAssessSelected;
	
	@ApiModelProperty(notes = "Quantitative Assessment process Assignee Details - Role, Person, SLA")
	private String qualAssessRPS;
	
	@ApiModelProperty(notes = "Behavioral Assessment process")
	private boolean isBehavAssessSelected;
	
	@ApiModelProperty(notes = "Behavioral Assessment process Assignee Details - Role, Person, SLA")
	private String behavAssessRPS;
	
	@ApiModelProperty(notes = "Video Assessment process")
	private boolean isVidAssessSelected;
	
	@ApiModelProperty(notes = "Video Assessment process Assignee Details - Role, Person, SLA")
	private String vidAssessRPS;
	
	@ApiModelProperty(notes = "First level Inteview process")
	private boolean isInterview1Selected;
	
	@ApiModelProperty(notes = "First level Inteview process Assignee Details - Role, Person, SLA")
	private String interview1RPS;
	
	@ApiModelProperty(notes = "Second level Inteview process")
	private boolean isInterview2Selected;
	
	@ApiModelProperty(notes = "Second level Inteview process Assignee Details - Role, Person, SLA")
	private String interview2RPS;
	
	@ApiModelProperty(notes = "HR Interview process")
	private boolean isHrInterviewSelected;
	
	@ApiModelProperty(notes = "HR Interview process Assignee Details - Role, Person, SLA")
	private String hrInterviewRPS;
	
	@ApiModelProperty(notes = "Document verification process")
	private boolean isDocumentationSelected;
	
	@ApiModelProperty(notes = "Document verification process Assignee Details - Role, Person, SLA")
	@Column(columnDefinition = "varchar(120)")
	private String documentationRPS;
	
	@ApiModelProperty(notes = "Offer rollout")
	private boolean isOfferSelected;
	
	@ApiModelProperty(notes = "Offer rollout process Assignee Details - Role, Person, SLA")
	private String offerRPS;
	
	
}
