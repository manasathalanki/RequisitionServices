package com.wissen.recruit.requisition.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.wissen.recruit.requisition.dto.CandidateDTO;
import com.wissen.recruit.requisition.exception.CandidateNotFoundException;
import com.wissen.recruit.requisition.exception.UsernameNotFoundException;

public interface CandidateService {

	ResponseEntity<CandidateDTO> createCandidate(CandidateDTO dto);

	ResponseEntity<CandidateDTO> updateCandidate(CandidateDTO dto) throws CandidateNotFoundException;

	ResponseEntity<List<CandidateDTO>> getAllCandidates();

	ResponseEntity<CandidateDTO> findByCandidateId(Integer id) throws CandidateNotFoundException;

	ResponseEntity<String> deleteCandidateById(Integer id) throws CandidateNotFoundException;

	ResponseEntity<List<CandidateDTO>> getAllCandidatesByUserName(String username) throws CandidateNotFoundException, UsernameNotFoundException;
}
