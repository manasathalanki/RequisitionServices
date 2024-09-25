package com.wissen.recruit.requisition.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain=true)
public class Candidate implements Serializable {

	private static final long serialVersionUID = -293671195579412994L;

	@Id
	@Column(nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer candidateId;

	private String firstName;

	private String lastName;

	private String experience;

	private String currentCTC;

	private String expectedCTC;

	private String location;

	private String prefLocation;

	private String appliedFor;

	private String recommendation;

	private String primarySkills;

	private String secondarySkills;
	@CreationTimestamp
	private Date createdDate;
	@UpdateTimestamp
	private Date modifiedDate;

	private String fileUrl;

	private String contentType;

	private String summary;

	private String source;
	
	private String addedByUsername;
	
	@ManyToOne
	@JoinColumn(name = "requisition_id")
	@JsonBackReference
	private Requisition requisition;

}
