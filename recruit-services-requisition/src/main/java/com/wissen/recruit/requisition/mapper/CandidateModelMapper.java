package com.wissen.recruit.requisition.mapper;

import com.wissen.recruit.requisition.dto.CandidateDTO;
import com.wissen.recruit.requisition.entity.Candidate;

public class CandidateModelMapper {

	private CandidateModelMapper() {
		super();
	}

	public static Candidate mapToModel(CandidateDTO dto) {
		return new Candidate()
				.setCandidateId(dto.getCandidateId())
				.setFirstName(dto.getFirstName())
				.setLastName(dto.getLastName())
				.setExperience(dto.getExperience())
				.setCurrentCTC(dto.getCurrentCTC())
				.setExpectedCTC(dto.getExpectedCTC())
				.setLocation(dto.getLocation())
				.setPrefLocation(dto.getPrefLocation())
				.setAppliedFor(dto.getAppliedFor())
				.setRecommendation(dto.getRecommendation())
				.setPrimarySkills(dto.getPrimarySkills())
				.setSecondarySkills(dto.getSecondarySkills())
				.setFileUrl(dto.getFileURL())
				.setContentType(dto.getContentType())
				.setSummary(dto.getSummary())
				.setSource(dto.getSource())
				.setAddedByUsername(dto.getAddedByUsername())
				.setRequisition(dto.getRequisition());
	}
	
}
