package com.wissen.recruit.requisition.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel("Grade")
@Entity
@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Grade implements Serializable {

	private static final long serialVersionUID = 8607502230081037121L;

	@ApiModelProperty(notes = "The Grade Id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int gradeId;

	@ApiModelProperty(notes = "Grade Label")
	@NotBlank
	private String label;
	
	@ApiModelProperty(notes = "Grade Value")
	@NotBlank
	private String value;

}
