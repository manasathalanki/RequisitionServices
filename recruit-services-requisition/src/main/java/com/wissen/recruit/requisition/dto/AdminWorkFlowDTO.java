package com.wissen.recruit.requisition.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "Sample Request Body of Admin Work Flow")
public class AdminWorkFlowDTO {

	@ApiModelProperty(notes = "The work flow Id")
	private int workFlowId;
	
	@ApiModelProperty(notes = "The work flow name")
	private String workFlowName;
	
	@ApiModelProperty(notes = "The work flow steps")
	private String workFlowSteps;

}
