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

@ApiModel("Hiring Manager")
@Setter
@Getter
@EqualsAndHashCode
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HiringManager implements Serializable {

	private static final long serialVersionUID = 1728275672580155594L;

	@ApiModelProperty(notes = "ID")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false)
	private Integer id;

	@ApiModelProperty(notes = "Hiring Manager Label")
	@NotBlank
	private String label;
	
	@ApiModelProperty(notes = "Hiring Manager Value")
	@NotBlank
	private String value;
}
