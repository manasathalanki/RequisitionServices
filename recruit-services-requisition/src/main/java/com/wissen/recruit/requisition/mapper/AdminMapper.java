package com.wissen.recruit.requisition.mapper;

import com.wissen.recruit.requisition.dto.AdminWorkFlowDTO;
import com.wissen.recruit.requisition.entity.AdminWorkFlow;

public class AdminMapper {

	private AdminMapper() {
		super();
	}

	public static AdminWorkFlowDTO workflowModelToDTO(AdminWorkFlow model) {
		return new AdminWorkFlowDTO().setWorkFlowId(model.getWorkFlowId())
				.setWorkFlowName(model.getWorkFlowName())
				.setWorkFlowSteps(model.getWorkFlowSteps());
	}
	
	public static AdminWorkFlow workflowDTOToModel(AdminWorkFlowDTO dto) {
		return new AdminWorkFlow().setWorkFlowId(dto.getWorkFlowId())
				.setWorkFlowName(dto.getWorkFlowName())
				.setWorkFlowSteps(dto.getWorkFlowSteps());
	}
}
