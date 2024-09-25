package com.wissen.recruit.requisition.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@ApiModel("Location Wise Openings")
public class LocationWiseOpenings  implements Serializable {

	private static final long serialVersionUID = 8211793115061060752L;
	
	@ApiModelProperty(notes = "Location wise openings id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false)
	private Integer id;
	
	@ApiModelProperty(notes = "Openings for the Location")
	@NotBlank
	private String location;
	
	@ApiModelProperty(notes = "no of openings in the respective location")
	@Min(1)
	private Integer openings;

}
