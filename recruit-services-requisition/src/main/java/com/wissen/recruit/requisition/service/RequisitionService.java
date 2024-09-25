package com.wissen.recruit.requisition.service;

import java.util.List;

import com.wissen.recruit.requisition.dto.RequisitionDTO;

public interface RequisitionService {

	RequisitionDTO createRequisition(RequisitionDTO requisition);
	List<RequisitionDTO> getAllRequisitions();
	RequisitionDTO getRequisitionById(int id);
	RequisitionDTO updateRequisition(RequisitionDTO requisition);
	RequisitionDTO deleteRequisitionById(int id);
	
}
