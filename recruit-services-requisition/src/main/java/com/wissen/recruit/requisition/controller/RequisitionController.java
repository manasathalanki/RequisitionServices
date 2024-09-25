package com.wissen.recruit.requisition.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import com.wissen.recruit.requisition.dto.RequisitionDTO;
import com.wissen.recruit.requisition.exception.InvalidIdException;
import com.wissen.recruit.requisition.service.RequisitionService;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@RestController
@RequestMapping("/requisition")
@EnableSwagger2
public class RequisitionController {

	@Autowired
	RequisitionService requisitionService;

	Logger logger = LoggerFactory.getLogger(RequisitionController.class);

	@GetMapping("/port")
	@ApiOperation(value = "getPortInformation", notes="Get port running this service",nickname = "getPort")
	public String getPortInformation(HttpServletRequest req) {
		return String.valueOf(req.getLocalPort());
	}

	@PostMapping("/")
	@ApiOperation(value = "createRequisition", notes="Create new Requisition",nickname = "createRequisition")
	public ResponseEntity<RequisitionDTO> createRequisition(@Valid @RequestBody RequisitionDTO requisition) {
		logger.info("create requisiontion controller method called... {}", requisition.getJobTitle());
		RequisitionDTO savedrequisition = requisitionService.createRequisition(requisition);

		logger.info("requsiontion created with id ... {}", savedrequisition.getRequisitionId());
		return new ResponseEntity<>(savedrequisition, HttpStatus.CREATED);
	}

	@GetMapping("/")
	@ApiOperation(value = "getAllRequisitions", notes="Get all Requisitions",nickname = "getAllRequisitions")
	public ResponseEntity<List<RequisitionDTO>> getAllRequisitions(){
		logger.info("requsiontion controller method - getAllRequisitions called... {}", "");
		List<RequisitionDTO> requisitionList = requisitionService.getAllRequisitions();
		
		logger.info("requsiontion controller - Fetched all the requisitions ... {}", "");
		return new ResponseEntity<>(requisitionList, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	@ApiOperation(value = "getRequisitionById", notes="Get requisition by Id",nickname = "getRequisitionById")
	public ResponseEntity<RequisitionDTO> getRequisitionById(@PathVariable("id") String requisitionId) throws InvalidIdException{
		Integer id = Integer.valueOf(requisitionId);
		if(id<=0) {
			throw new InvalidIdException(ErrorMessages.INVALID_REQUISITION_ID + id);
		}
		logger.info("requsiontion controller method - getRequisitionById called... {}", id);
		RequisitionDTO requisition = requisitionService.getRequisitionById(id);
		
		logger.info("requisition details fetched for the id ...{}", id);
		return new ResponseEntity<>(requisition, HttpStatus.OK);
	}
	
	@PutMapping("/")
	@ApiOperation(value = "updateRequisition", notes="Update requisition",nickname = "updateRequisition")
	public ResponseEntity<RequisitionDTO> updateRequisition(@Valid @RequestBody RequisitionDTO requisition) throws InvalidIdException {
		if(Integer.valueOf(requisition.getRequisitionId())<=0) {
			logger.error("Requsiontion controller - Invalid rquisition Id ... {}", requisition.getRequisitionId());
			throw new InvalidIdException(ErrorMessages.INVALID_REQUISITION_ID + requisition.getRequisitionId());
		}
		
		logger.info("requsiontion controller method - updateRequisition called... {}", requisition.getRequisitionId());
		RequisitionDTO updatedrequisition = requisitionService.updateRequisition(requisition);
		
		logger.info("requisition details updated for the id ...{}", requisition.getRequisitionId());
		return new ResponseEntity<>(updatedrequisition, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "deleteRequisitionById", notes="Delete requisition by Id",nickname = "deleteRequisitionById")
	public ResponseEntity<RequisitionDTO> deleteRequisitionById(@PathVariable("id") String requisitionId) throws InvalidIdException{
		Integer id = Integer.valueOf(requisitionId);
		if(id<=0) {
			logger.error("Requsiontion controller - Invalid rquisition Id ... {}", requisitionId);
			throw new InvalidIdException(ErrorMessages.INVALID_REQUISITION_ID + requisitionId);
		}
		
		logger.info("requsiontion controller method - deleteRequisitionById called... {}", id);
		RequisitionDTO deletedRequisition = requisitionService.deleteRequisitionById(id);
		
		logger.info("requisition deleted ...{}", id);
		return new ResponseEntity<>(deletedRequisition, HttpStatus.OK);
	}
	
}
