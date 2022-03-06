package com.phamquyen.cafeslide.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.phamquyen.cafeslide.domain.Category;
import com.phamquyen.cafeslide.domain.Product;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Long>{
	
	@Query("SELECT c FROM Category c WHERE "
			+ "CONCAT(c.categoryId, ' ', c.categoryName) "
			+ "LIKE %:keyword%")
	Page<Category> findAll(Pageable page, String keyword);
	
	public Category findByCategoryName(String categoryName);
}
