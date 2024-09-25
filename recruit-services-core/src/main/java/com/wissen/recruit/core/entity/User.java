package com.wissen.recruit.core.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@ApiModel("User")
@SuppressWarnings("serial")
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "userdetails")
public class User implements UserDetails {
	@Id
	@ApiModelProperty(notes = "The unique Id user")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@ApiModelProperty(notes = "The name of the user")
	@Column(name = "username", nullable = false)
	private String username;

	@JsonIgnore
	@ApiModelProperty(notes = "The password of the user")
	@Column(name = "password", nullable = false)
	private String password;

	@ApiModelProperty(notes = "The email id of the user")
	@Column(name = "email", nullable = false)
	private String email;

	@ApiModelProperty(notes = "The phone number of the user")
	@Column(name = "phone", nullable = false)
	private String phone;

	@ApiModelProperty(notes = "The firm name of the user")
	@Column(name = "firmname", nullable = false)
	private String firmname;

	@ApiModelProperty(notes = "user created date")
	@Column(name = "createdDate", nullable = false)
	private Date createdDate;

	@ApiModelProperty(notes = "last modified date")
	@Column(name = "modifiedDate", nullable = false)
	private Date modifiedDate;

	@ApiModelProperty(notes = "recent logged in date")
	@Column(name = "lastLoggedInDate", nullable = false)
	private Date lastLoggedInDate;

	@ApiModelProperty(notes = "user is Active")
	@Column(name = "enabled", nullable = false)
	private boolean enabled;

	@JsonIgnore
	@ApiModelProperty(notes = "user roles")
	@ManyToMany
	@JoinTable(name = "users_roles", 
		joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private Collection<Role> roles;

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
		
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for(Role role:roles) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		return authorities;
	}

}
