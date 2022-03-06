package com.phamquyen.cafeslide.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phamquyen.cafeslide.domain.Role;
import com.phamquyen.cafeslide.repository.RolesRepository;
import com.phamquyen.cafeslide.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	RolesRepository rolesRepository;

	@Override
	public Role findByRoleName(String roleName) {
		return rolesRepository.findByRoleName(roleName);
	}
	
	
}
