package com.wissen.recruit.requisition.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.wissen.recruit.requisition.dto.CandidateDTO;
import com.wissen.recruit.requisition.entity.Candidate;
import com.wissen.recruit.requisition.exception.CandidateNotFoundException;
import com.wissen.recruit.requisition.exception.UsernameNotFoundException;
import com.wissen.recruit.requisition.mapper.CandidateDTOMapper;
import com.wissen.recruit.requisition.mapper.CandidateModelMapper;
import com.wissen.recruit.requisition.mapper.RequisitionMapper;
import com.wissen.recruit.requisition.repository.CandidateRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class CandidateServiceImpl implements CandidateService {

	@Autowired
	CandidateRepository candidateRepository;
	
	@Autowired
	RequisitionService requisitionService;

	@Override
	public ResponseEntity<CandidateDTO> createCandidate(CandidateDTO dto) {

		Candidate newCandidate = CandidateModelMapper.mapToModel(dto);
		log.info("Candidate Service - Saving the candidate into DB...", newCandidate.getFirstName());
		candidateRepository.save(newCandidate);
		log.info("Candidate Service - Saved the Candidate into DB.Returning the Response.",
				newCandidate.getFirstName());
		return new ResponseEntity<>(CandidateDTOMapper.mapToDTO(newCandidate), HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<CandidateDTO> updateCandidate(CandidateDTO dto)
			throws CandidateNotFoundException {
		validateCandidate(dto.getCandidateId());
		Candidate candidate = CandidateModelMapper.mapToModel(dto);
		log.info("Candidate Service - Updating the Candidate detail stored in DB...");
		Candidate updatedCandidate = candidateRepository.save(candidate);
		updatedCandidate.setRequisition(RequisitionMapper.DTOtoModel(requisitionService.getRequisitionById(updatedCandidate.getRequisition().getRequisitionId())));
		log.info("Candidate Service - Candidate detail updated in DB.Returning the Response.");
		return new ResponseEntity<>(CandidateDTOMapper.mapToDTO(updatedCandidate), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<CandidateDTO>> getAllCandidates() {
		log.info("Candidate Service - Fetching All Candidates from DB...");
		List<Candidate> allCandidates = candidateRepository.findAll();
		log.info("Candidate Service - All Candidates Fetched from DB.");

		log.info("Candidate Service - Converting Candidate Models to DTO...");
		List<CandidateDTO> responseDTO = allCandidates.stream().map(CandidateDTOMapper::mapToDTO)
				.collect(Collectors.toList());
		log.info("Candidate Service - Converted Candidate Models to DTO.Returning the Response.");

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<CandidateDTO> findByCandidateId(Integer id) throws CandidateNotFoundException {
		Candidate foundCand = validateCandidate(id);
		return new ResponseEntity<>(CandidateDTOMapper.mapToDTO(foundCand), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> deleteCandidateById(Integer id) throws CandidateNotFoundException {
		Candidate foundCand = validateCandidate(id);
		candidateRepository.delete(foundCand);
		return new ResponseEntity<>("Candidate Deleted", HttpStatus.OK);
	}

	public Candidate validateCandidate(Integer id) throws CandidateNotFoundException {
		log.info("Candidate Service - Checking Whether Candidate is Present or not in DB...");
		Candidate foundCand = candidateRepository.findById(id).orElseThrow(() -> {
			log.error("Candidate Service - Candidate is not Present in DB.");
			return new CandidateNotFoundException("Candidate Service - Candidate Not Found in the Database");
		});
		log.info("Candidate Service - Candidate is Present in DB.");

		return foundCand;
	}

	@Override
	public ResponseEntity<List<CandidateDTO>> getAllCandidatesByUserName(String username)
			throws CandidateNotFoundException, UsernameNotFoundException {
		log.info("Candidate Service - Fetching All Candidates from DB by username...");
		List<Candidate> allCandidates = candidateRepository.findByAddedByUsername(username).orElseThrow(() -> {
			log.error("Candidate Service - Candidate is not Present in DB for this username provided.");
			return new UsernameNotFoundException("Candidate is not Present in DB for this username provided.");
		});
		log.info("Candidate Service - All Candidates Fetched from DB by username.");

		if(allCandidates.isEmpty()) {
			log.error("No Candidates found for this username");
			throw new CandidateNotFoundException("No Candidates found for this username");
		}
			
		log.info("Candidate Service - Converting Candidate Models to DTO...");
		List<CandidateDTO> responseDTO = allCandidates.stream().map(CandidateDTOMapper::mapToDTO)
				.collect(Collectors.toList());
		log.info("Candidate Service - Converted Candidate Models to DTO.Returning the Response.");

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

}
