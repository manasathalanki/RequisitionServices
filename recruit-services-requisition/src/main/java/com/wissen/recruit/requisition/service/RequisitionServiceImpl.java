package com.wissen.recruit.requisition.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wissen.recruit.requisition.constants.ErrorMessages;
import com.wissen.recruit.requisition.dto.RequisitionDTO;
import com.wissen.recruit.requisition.entity.Requisition;
import com.wissen.recruit.requisition.exception.RequisitionNotFoundException;
import com.wissen.recruit.requisition.mapper.RequisitionMapper;
import com.wissen.recruit.requisition.repository.RequisitionRepository;

@Service
public class RequisitionServiceImpl implements RequisitionService {

	final Logger logger = LoggerFactory.getLogger(RequisitionServiceImpl.class);

	@Autowired
	RequisitionRepository requisitionRepository;
	
	@Override
	public RequisitionDTO createRequisition(RequisitionDTO requisition) {
		Requisition model = RequisitionMapper.DTOtoModel(requisition);
		logger.info("create requisition service method - createRequisition called... {}", requisition.getJobTitle());
		Requisition savedrequisition = requisitionRepository.save(model);
		
		logger.info("requisition service - Requisition saved ... {}", savedrequisition.getJobTitle());
		return RequisitionMapper.modeltoDTO(savedrequisition);
	}

	@Override
	public List<RequisitionDTO> getAllRequisitions() {
		logger.info("requisition service method - getAllRequisitions called... {}", "");
		List<Requisition> requisitionsList = requisitionRepository.findAll();
		
		List<RequisitionDTO> dtolist = requisitionsList.stream().map(RequisitionMapper::modeltoDTO)
				.collect(Collectors.toList());
		logger.info("requisition service - Fetched all the requisitions ... {}", "");
		return dtolist;
	}
	
	@Override
	public RequisitionDTO getRequisitionById(int id) {
		logger.info("requisition service method - getRequisitionById called... {}", id);
		Optional<Requisition> optionalrequisition =  requisitionRepository.findById(id);
		
		if(optionalrequisition.isPresent()) {
			Requisition requisition = optionalrequisition.get();
			logger.info("requisition service - Requisition fetched for the id ... {}", id);
			return RequisitionMapper.modeltoDTO(requisition);
		}else {
			logger.error("requisition service - Requisition not Found for the given Id ... {}", id);
			throw new RequisitionNotFoundException(ErrorMessages.REQUISTION_DETAILS_NOT_FOUND_FOR_ID+ id);
		}
	}
	
	@Override
	public RequisitionDTO updateRequisition(RequisitionDTO dto) {
		this.getRequisitionById(dto.getRequisitionId());
		logger.info("requisition service method - updateRequisition called... {}", dto);
		Requisition updatedRequisition = requisitionRepository.save(RequisitionMapper.DTOtoModel(dto));
		logger.info("requisition service - Requisition Updated ... {}", updatedRequisition.getRequisitionId());
		return RequisitionMapper.modeltoDTO(updatedRequisition);
	}

	@Override
	public RequisitionDTO deleteRequisitionById(int id) {
		logger.info("requisition service method - deleteRequisitionById called... {}", id);
		RequisitionDTO dto = this.getRequisitionById(id);
		
		requisitionRepository.deleteById(id);
		logger.info("requisition details deleted for the id ... {}", id);
		
		return dto;
	}
}
