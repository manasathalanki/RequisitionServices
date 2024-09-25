package com.wissen.recruit.requisition.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;


@Setter
@Getter
@EqualsAndHashCode
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "requisition")
@Accessors(chain=true)
public class Requisition implements Serializable{

	private static final long serialVersionUID = -7055498569243621029L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false)
	private int requisitionId; 
	
	@NotBlank
	private String jobTitle;
	
	private String band;
	
	@NotBlank
	private String grade;
	
	@NotBlank
	private String minExperience;
	
	@NotBlank
	private String maxExperience;
	
	private boolean isCampus;
	
	@NotBlank
	private String requisitionType;
	
	private String tatDays;
	
	private String tatHours;
	
	@NotNull
	private String hiringManager;
	
	@DateTimeFormat
	private Date startDate;
	
	@DateTimeFormat
	private Date closingDate;
	
	@NotBlank
	private String minCtc;
	
	@NotBlank
	private String maxCtc;
	
	@Valid
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "requisition_id")
	private List<LocationWiseOpenings> locationWiseOpeningsList;
	
	private String shiftTiming;
	
	private String functionalArea;
	
	private String team;
	
	private String client;
	
	private String project;
	
	@Valid
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "hiringprocess_id")
	private HiringProcess hiringProcess;
	
	@Valid
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "jobDescId")
	private JobDescription jobDescription;
	
	@CreationTimestamp
	private Date createdDate;
	
	@UpdateTimestamp
	private Date modifiedDate;
	
	@Valid
	@OneToMany(mappedBy = "requisition", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	private List<Candidate> candidateList;
	
	
	
}
