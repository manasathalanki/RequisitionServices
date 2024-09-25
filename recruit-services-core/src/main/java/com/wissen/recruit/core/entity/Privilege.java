package com.wissen.recruit.core.entity;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Privilege implements Serializable {

	private static final long serialVersionUID = -2657722020185733491L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	@ManyToMany(mappedBy="privileges")
	private Collection<Role> roles;

	public Privilege(String name) {
		super();
		this.name = name;
	}
		
	
}
