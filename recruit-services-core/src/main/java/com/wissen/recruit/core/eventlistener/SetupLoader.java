package com.wissen.recruit.core.eventlistener;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.wissen.recruit.core.entity.Privilege;
import com.wissen.recruit.core.entity.Role;
import com.wissen.recruit.core.repository.PrivilegeRepository;
import com.wissen.recruit.core.repository.RoleRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class SetupLoader implements ApplicationListener<ContextRefreshedEvent> {

	boolean alreadySetup = false;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PrivilegeRepository privilegeRepository;

	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (alreadySetup) {
			log.info("Role and Privilege Setup Already Completed...");
			return;
		}
			
		Privilege createPrivilege = createPrivilegeIfNotFound("CREATE_PRIVILEGE");
		Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
		Privilege updatePrivilege = createPrivilegeIfNotFound("UPDATE_PRIVILEGE");
		Privilege deletePrivilege = createPrivilegeIfNotFound("DELETE_PRIVILEGE");

		List<Privilege> superAdminPrivilege = Arrays.asList(createPrivilege, readPrivilege, updatePrivilege,
				deletePrivilege);
		List<Privilege> adminPrivilege = Arrays.asList(createPrivilege, readPrivilege, updatePrivilege);

		createRoleIfNotFound("ROLE_SUPERADMIN", superAdminPrivilege);
		createRoleIfNotFound("ROLE_ADMIN", adminPrivilege);
		createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));

		log.info("Role and Privilege Setup Completed...");
		alreadySetup = true;
	}

	private Privilege createPrivilegeIfNotFound(String privilegeName) {
		Privilege privilege = privilegeRepository.findByName(privilegeName);
		if (privilege == null) {
			privilege = new Privilege(privilegeName);
			log.info("Saving Privileges into DB...");
			privilegeRepository.save(privilege);
			log.info("Saved Privileges into DB...");
		}
		return privilege;
	}

	private Role createRoleIfNotFound(String roleName, List<Privilege> privileges) {
		Role role = roleRepository.findByName(roleName);
		if (role == null) {
			role = new Role(roleName);
			role.setPrivileges(privileges);
			log.info("Saving Roles into DB...");
			roleRepository.save(role);
			log.info("Saved Roles into DB...");
		}
		return role;
	}
}
