package com.wissen.recruit.requisition.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wissen.recruit.requisition.dto.CandidateDTO;
import com.wissen.recruit.requisition.exception.CandidateNotFoundException;
import com.wissen.recruit.requisition.exception.UsernameNotFoundException;
import com.wissen.recruit.requisition.service.CandidateService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/requisition/candidate")
public class CandidateController {

	@Autowired
	CandidateService candidateService;

	@ApiOperation(value = "createCandidate", notes = "Create new candidate", nickname = "createCandidate")
	@PostMapping("/")
	public ResponseEntity<CandidateDTO> createCandidate(@RequestBody CandidateDTO dto) {
		return candidateService.createCandidate(dto);
	}

	@ApiOperation(value = "updateCandidate", notes = "Update existing candidate", nickname = "updateCandidate")
	@PutMapping("/")
	public ResponseEntity<CandidateDTO> updateCandidate(@RequestBody CandidateDTO dto) throws CandidateNotFoundException {
		return candidateService.updateCandidate(dto);
	}

	@ApiOperation(value = "deleteCandidateById", notes = "Delete Existing Candidate by Provided id", nickname = "deleteCandidateById")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCandidate(@PathVariable("id") Integer id) throws CandidateNotFoundException {
		return candidateService.deleteCandidateById(id);
	}

	@ApiOperation(value = "getAllCandidates", notes = "Get All Candidates", nickname = "getAllCandidates")
	@GetMapping("/")
	public ResponseEntity<List<CandidateDTO>> getAllCandidates() {
		return candidateService.getAllCandidates();
	}

	@ApiOperation(value = "getCandidate", notes = "Get Particular Candidate", nickname = "getCandidate")
	@GetMapping("/{id}")
	public ResponseEntity<CandidateDTO> getCandidate(@PathVariable("id") Integer id) throws CandidateNotFoundException {
		return candidateService.findByCandidateId(id);
	}
	
	@ApiOperation(value = "getAllCandidatesByAddedUsername", notes = "Get All Candidates By Added Username", nickname = "getAllCandidates By Added Username")
	@GetMapping("/addedbyuser")
	public ResponseEntity<List<CandidateDTO>> getAllCandidatesByUsername(@RequestParam("name") String username) throws CandidateNotFoundException, UsernameNotFoundException {
		return candidateService.getAllCandidatesByUserName(username);
	} 

}
