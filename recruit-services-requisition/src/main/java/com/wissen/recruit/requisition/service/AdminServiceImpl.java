package com.wissen.recruit.requisition.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wissen.recruit.requisition.constants.ErrorMessages;
import com.wissen.recruit.requisition.dto.AdminWorkFlowDTO;
import com.wissen.recruit.requisition.entity.AdminWorkFlow;
import com.wissen.recruit.requisition.exception.WorkflowNotFoundException;
import com.wissen.recruit.requisition.mapper.AdminMapper;
import com.wissen.recruit.requisition.repository.AdminWorkFlowRepository;

@Service
public class AdminServiceImpl implements AdminService{

	final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
	
	@Autowired
	AdminWorkFlowRepository workflowrepository;

	@Override
	public AdminWorkFlowDTO saveworkflow(AdminWorkFlowDTO dto) {
		AdminWorkFlow model = AdminMapper.workflowDTOToModel(dto);
		logger.info("Admin Service - save work flow method called ...{}", dto.getWorkFlowName());
		AdminWorkFlow savedflow = workflowrepository.save(model);
		logger.info("Admin Service - work flow details saved with an id ...{}", savedflow.getWorkFlowId());
		return AdminMapper.workflowModelToDTO(savedflow);
	}

	@Override
	public List<AdminWorkFlowDTO> getAllWorkflows() {
		logger.info("Admin service - getAllWorkflows called... {}", "");
		List<AdminWorkFlow> requisitionsList = workflowrepository.findAll();
		List<AdminWorkFlowDTO> dtolist = requisitionsList.stream().map(AdminMapper::workflowModelToDTO).collect(Collectors.toList());
		logger.info("Admin service - Fetched saved workflows ... {}", "");
		return dtolist;
	}

	@Override
	public AdminWorkFlowDTO getWorkflowById(Integer id) {
		logger.info("Admin service - getWorkflowById called... {}", id);
		Optional<AdminWorkFlow> workflow = workflowrepository.findById(id);
		if(workflow.isPresent()) {
			logger.info("Admin service - Fetched workflow details for the id... {}", id);
			return AdminMapper.workflowModelToDTO(workflow.get());
		}else {
			logger.error("Admin service - workflow not Found for the given Id ... {}", id);
			throw new WorkflowNotFoundException(ErrorMessages.WORKFLOW_NOT_FOUND_FOR_ID + id);
		}
	}

	@Override
	public AdminWorkFlowDTO deleteWorkFlowById(Integer id) {
		logger.info("admin service method - deleteWorkFlowById called... {}", id);
		AdminWorkFlowDTO dto = this.getWorkflowById(id);
		workflowrepository.deleteById(id);
		logger.info("workflow details deleted for the id ... {}", id);
		return dto;
	}

	@Override
	public AdminWorkFlowDTO updateWorkflow(AdminWorkFlowDTO dto) {
		this.getWorkflowById(dto.getWorkFlowId());
		logger.info("Admin service - updateWorkflow method called... {}", dto);
		AdminWorkFlow updatedWorkflow =  workflowrepository.save(AdminMapper.workflowDTOToModel(dto));
		logger.info("Admin service - workflow Updated ... {}", updatedWorkflow.getWorkFlowId());
		return AdminMapper.workflowModelToDTO(updatedWorkflow);
	}

}
