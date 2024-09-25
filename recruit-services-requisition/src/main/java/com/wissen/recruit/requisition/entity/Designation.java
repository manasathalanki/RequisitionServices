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

@ApiModel("Designation")
@Entity
@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Designation implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "The Designation Id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int designationId;

	@ApiModelProperty(notes = "Designation Label")
	@NotBlank
	private String label;
	
	@ApiModelProperty(notes = "Designation Value")
	@NotBlank
	private String value;

}
