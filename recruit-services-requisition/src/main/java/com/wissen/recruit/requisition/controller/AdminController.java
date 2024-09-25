package com.wissen.recruit.requisition.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wissen.recruit.requisition.constants.ErrorMessages;
import com.wissen.recruit.requisition.dto.AdminWorkFlowDTO;
import com.wissen.recruit.requisition.exception.InvalidIdException;
import com.wissen.recruit.requisition.service.AdminService;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@RestController
@RequestMapping("/requisition/admin")
@EnableSwagger2
public class AdminController {

	@Autowired
	AdminService service;
	
	Logger logger = LoggerFactory.getLogger(AdminController.class);

	@PostMapping("/workflow")
	public ResponseEntity<AdminWorkFlowDTO> saveWorkflow(@Valid @RequestBody AdminWorkFlowDTO dto) {
		logger.info("Admin controller - save workflow method called... {}", dto.getWorkFlowName());
		AdminWorkFlowDTO savedworkflow = service.saveworkflow(dto);
		logger.info("workflow details saved with id ... {}", savedworkflow.getWorkFlowId());
		return new ResponseEntity<>(savedworkflow, HttpStatus.CREATED);		
	}
	
	@GetMapping("/workflow/")
	public ResponseEntity<List<AdminWorkFlowDTO>> getWorkflows(){
		logger.info("Admin controller - getworkflow method called... {}", "");
		List<AdminWorkFlowDTO> workflowList = service.getAllWorkflows();
		logger.info("Admin controller - Fetched saved workflows ... {}", "");
		return new ResponseEntity<>(workflowList, HttpStatus.OK);
	}
	
	@GetMapping("/workflow/{id}")
	public ResponseEntity<AdminWorkFlowDTO> getWorkflowById(@PathVariable("id") String workflowId) throws InvalidIdException{
		Integer id = Integer.parseInt(workflowId);
		if(id <=0) throw new InvalidIdException(ErrorMessages.INVALID_WORKFLOW_ID + id);
		
		logger.info("Admin controller - getWorkflowById method called... {}", id);
		AdminWorkFlowDTO dto = service.getWorkflowById(id);
		logger.info("Admin controller - Work flow details fetched for the id ...{}", id);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	@DeleteMapping("/workflow/{id}")
	public ResponseEntity<AdminWorkFlowDTO> deleteWorkflowById(@PathVariable("id") String workflowId) throws InvalidIdException {
		Integer id = Integer.parseInt(workflowId);
		if(id <=0) throw new InvalidIdException(ErrorMessages.INVALID_WORKFLOW_ID + id);
		
		logger.info("Admin controller - deleteWorkflowById called... {}", id);
		AdminWorkFlowDTO dto = service.deleteWorkFlowById(id);
		logger.info("workflow details deleted for the id ... {}", id);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	@PutMapping("/workflow/")
	public ResponseEntity<AdminWorkFlowDTO> updateWorkflow(@Valid @RequestBody AdminWorkFlowDTO dto) throws InvalidIdException{
		if(Integer.valueOf(dto.getWorkFlowId())<=0) {
			logger.error("Admin controller - UpdateWorkflow method ... {}", dto.getWorkFlowId());
			throw new InvalidIdException(ErrorMessages.INVALID_WORKFLOW_ID + dto.getWorkFlowId());
		}
		logger.info("workflow controller - updateWorkflow called... {}", dto.getWorkFlowId());
		AdminWorkFlowDTO updatedWorkflow = service.updateWorkflow(dto);
		logger.info("workflow details updated for the id ...{}", dto.getWorkFlowId());
		return new ResponseEntity<>(updatedWorkflow, HttpStatus.OK);
	}
}
