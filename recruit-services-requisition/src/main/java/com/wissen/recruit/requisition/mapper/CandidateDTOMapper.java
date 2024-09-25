package com.wissen.recruit.requisition.mapper;

import com.wissen.recruit.requisition.dto.CandidateDTO;
import com.wissen.recruit.requisition.entity.Candidate;

public class CandidateDTOMapper {

	private CandidateDTOMapper() {
		super();
	}

	@SuppressWarnings("deprecation")
	public static CandidateDTO mapToDTO(Candidate model) {
		return new CandidateDTO()
				.setCandidateId(model.getCandidateId())
				.setFirstName(model.getFirstName())
				.setLastName(model.getLastName())
				.setExperience(model.getExperience())
				.setCurrentCTC(model.getCurrentCTC())
				.setExpectedCTC(model.getExpectedCTC())
				.setLocation(model.getLocation())
				.setPrefLocation(model.getPrefLocation())
				.setAppliedFor(model.getAppliedFor())
				.setRecommendation(model.getRecommendation())
				.setPrimarySkills(model.getPrimarySkills())
				.setSecondarySkills(model.getSecondarySkills())
				.setFileURL(model.getFileUrl())
				.setContentType(model.getContentType())
				.setSummary(model.getSummary())
				.setSource(model.getSource())
				.setAddedByUsername(model.getAddedByUsername())
				.setRequisition(model.getRequisition());
	}
}
