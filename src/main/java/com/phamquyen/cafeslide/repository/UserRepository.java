package com.phamquyen.cafeslide.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.phamquyen.cafeslide.domain.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long>{
	
	@Query("SELECT u FROM User u where "
			+ "CONCAT(u.userId, ' ', u.fullname, ' ', u.email, ' ', u.phone, ' ', u.address)"
			+ "LIKE %:keyword%")
	Page<User> findAll(Pageable page, String keyword);   
	
	public User findByEmail(String email);
}
