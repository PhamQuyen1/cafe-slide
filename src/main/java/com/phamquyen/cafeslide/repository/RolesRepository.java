package com.phamquyen.cafeslide.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.phamquyen.cafeslide.domain.Role;

@Repository
public interface RolesRepository extends JpaRepository<Role, Long>{
	
	public Role findByRoleName(String roleName);

}
