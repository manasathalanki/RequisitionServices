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

@ApiModel("Band")
@Entity
@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Band implements Serializable {
	
	private static final long serialVersionUID = -409695871989989115L;

	@ApiModelProperty(notes = "The Band Id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bandId;

	@ApiModelProperty(notes = "Band Label")
	@NotBlank
	private String label;
	
	@ApiModelProperty(notes = "Band Value")
	@NotBlank
	private String value;
	

}
