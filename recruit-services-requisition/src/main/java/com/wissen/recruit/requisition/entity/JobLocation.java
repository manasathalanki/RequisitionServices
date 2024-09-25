package com.wissen.recruit.requisition.entity;

import java.io.Serializable;
import javax.persistence.Column;
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

@ApiModel("Job Location")
@Setter
@Getter
@EqualsAndHashCode
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobLocation implements Serializable {

	private static final long serialVersionUID = 1728275672580155594L;

	@ApiModelProperty(notes = "Location ID")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false)
	private Integer id;

	@ApiModelProperty(notes = "Job Location Label")
	@NotBlank
	private String label;
	
	@ApiModelProperty(notes = "Job Location Value")
	@NotBlank
	private String value;
}
