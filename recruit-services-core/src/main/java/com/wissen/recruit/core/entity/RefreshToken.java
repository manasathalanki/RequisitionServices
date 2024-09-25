package com.wissen.recruit.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="refresh_token_details")
public class RefreshToken {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="tokenid",nullable=false)
	private Integer id;
	@Column(name="userid",nullable=false)
	private Integer userid;
	@Column(name="username",nullable=false)
	private String username;
	@Column(name="tokenValue",nullable=false)
	private String tokenValue;
	@Column(name="created_date",nullable=false)
	private Date createdDate;
	@Column(name="tokenActive",nullable=false)
	private boolean tokenActive;
		
}
