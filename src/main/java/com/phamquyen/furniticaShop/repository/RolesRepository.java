package com.phamquyen.furniticaShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.phamquyen.furniticaShop.domain.Role;

@Repository
public interface RolesRepository extends JpaRepository<Role, Long>{
	
	public Role findByRoleName(String roleName);

}
