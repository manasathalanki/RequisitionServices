package com.wissen.recruit.requisition.service;

import java.util.List;

import com.wissen.recruit.requisition.dto.AdminWorkFlowDTO;

public interface AdminService {

	AdminWorkFlowDTO saveworkflow(AdminWorkFlowDTO dto);

	List<AdminWorkFlowDTO> getAllWorkflows();

	AdminWorkFlowDTO getWorkflowById(Integer id);

	AdminWorkFlowDTO deleteWorkFlowById(Integer id);

	AdminWorkFlowDTO updateWorkflow(AdminWorkFlowDTO dto);

}
