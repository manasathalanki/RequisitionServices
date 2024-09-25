package com.wissen.recruit.requisition.mapper;

import com.wissen.recruit.requisition.dto.RequisitionDTO;
import com.wissen.recruit.requisition.entity.Requisition;

public class RequisitionMapper {
	
	private RequisitionMapper() {
		super();
	}

	public static RequisitionDTO modeltoDTO(Requisition model) {
		return new RequisitionDTO().setBand(model.getBand())
				.setCampus(model.isCampus())
				.setCandidateList(model.getCandidateList())
				.setClient(model.getClient())
				.setClosingDate(model.getClosingDate())
				.setCreatedDate(model.getCreatedDate())
				.setFunctionalArea(model.getFunctionalArea())
				.setGrade(model.getGrade())
				.setHiringManager(model.getHiringManager())
				.setHiringProcess(model.getHiringProcess())
				.setJobDescription(model.getJobDescription())
				.setJobTitle(model.getJobTitle())
				.setLocationWiseOpeningsList(model.getLocationWiseOpeningsList())
				.setMaxCtc(model.getMaxCtc())
				.setMinCtc(model.getMinCtc())
				.setMaxExperience(model.getMaxExperience())
				.setMinExperience(model.getMinExperience())
				.setModifiedDate(model.getModifiedDate())
				.setProject(model.getProject())
				.setRequisitionId(model.getRequisitionId())
				.setRequisitionType(model.getRequisitionType())
				.setStartDate(model.getStartDate())
				.setTatDays(model.getTatDays())
				.setTatHours(model.getTatHours())
				.setShiftTiming(model.getShiftTiming())
				.setTeam(model.getTeam());
	}
	
	public static Requisition DTOtoModel(RequisitionDTO dto) {
		return new Requisition().setBand(dto.getBand())
				.setCampus(dto.isCampus())
				.setCandidateList(dto.getCandidateList())
				.setClient(dto.getClient())
				.setClosingDate(dto.getClosingDate())
				.setCreatedDate(dto.getCreatedDate())
				.setFunctionalArea(dto.getFunctionalArea())
				.setGrade(dto.getGrade())
				.setHiringManager(dto.getHiringManager())
				.setHiringProcess(dto.getHiringProcess())
				.setJobDescription(dto.getJobDescription())
				.setJobTitle(dto.getJobTitle())
				.setLocationWiseOpeningsList(dto.getLocationWiseOpeningsList())
				.setMaxCtc(dto.getMaxCtc())
				.setMaxExperience(dto.getMaxExperience())
				.setMinCtc(dto.getMinCtc())
				.setMinExperience(dto.getMinExperience())
				.setModifiedDate(dto.getModifiedDate())
				.setProject(dto.getProject())
				.setRequisitionId(dto.getRequisitionId())
				.setRequisitionType(dto.getRequisitionType())
				.setStartDate(dto.getStartDate())
				.setTatDays(dto.getTatDays())
				.setTatHours(dto.getTatHours())
				.setShiftTiming(dto.getShiftTiming())
				.setTeam(dto.getTeam());
	}
}
