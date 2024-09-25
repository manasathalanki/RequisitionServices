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

@ApiModel("Skills")
@Setter
@Getter
@EqualsAndHashCode
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Skills implements Serializable {

	private static final long serialVersionUID = 6387291520093277337L;

	@ApiModelProperty(notes = "Skill ID")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false)
	private Integer id;

	@ApiModelProperty(notes = "Skill Label")
	@NotBlank
	private String label;
	
	@ApiModelProperty(notes = "Skill Value")
	@NotBlank
	private String value;
}
