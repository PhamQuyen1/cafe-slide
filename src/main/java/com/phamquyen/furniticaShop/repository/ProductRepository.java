package com.phamquyen.furniticaShop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.phamquyen.furniticaShop.domain.Product;

@Repository
public interface ProductRepository extends PagingAndSortingRepository <Product, Long>{

	@Query("SELECT p FROM Product p WHERE "
			+"CONCAT(p.productId,' ', p.productName, ' ', p.enteredDate, ' ', p.unitPrice, ' ', p.size, ' ', p.quantity)"
			+ "  LIKE %:keyword%")
	public Page<Product> findAll(Pageable pageable, String keyword);

	public Product findByProductName(String productName);
	
	@Query("SELECT p FROM Product p WHERE p.category.categoryId = :categoryId")
	public Page<Product> findAllByCategory(Long categoryId, Pageable pageable);

}
