package com.wissen.recruit.requisition.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class JobDescription implements Serializable{

	private static final long serialVersionUID = 3926720501178330162L;

	@Id
	@Column(nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(notes = "Job Description Id")
	private Integer jobDescId;
	
	@ApiModelProperty(notes = "Education")
	@NotBlank
	private String education;

	@ApiModelProperty(notes = "Job Description")
	@NotBlank
	private String description;

	@ApiModelProperty(notes = "Roles and Responsbilities")
	private String rolesAndResponsibilty;
	
	@ApiModelProperty(notes = "Primary skills")
	@NotBlank
	private String primarySkills;
	
	@ApiModelProperty(notes = "Secondary skills")
	@NotBlank
	private String secondarySkills;
	
	@ApiModelProperty(notes = "Additional skills")
	private String additionalSkills;

}
