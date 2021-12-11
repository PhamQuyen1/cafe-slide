package com.phamquyen.furniticaShop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phamquyen.furniticaShop.domain.Role;
import com.phamquyen.furniticaShop.repository.RolesRepository;
import com.phamquyen.furniticaShop.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	RolesRepository rolesRepository;

	@Override
	public Role findByRoleName(String roleName) {
		return rolesRepository.findByRoleName(roleName);
	}
	
	
}
