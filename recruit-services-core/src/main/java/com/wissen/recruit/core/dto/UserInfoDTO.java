package com.wissen.recruit.core.dto;

import java.util.Collection;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {

	private String timeStamp;
	private int status;
	private String username;
	private String phone;
	private String email;
	private String firmname;
	private Collection<String> roles;
	private Date createdDate;
	private Date lastLoggedInDate;
	private Date modifiedDate;
}
