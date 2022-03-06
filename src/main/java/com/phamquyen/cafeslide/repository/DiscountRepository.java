package com.phamquyen.cafeslide.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.phamquyen.cafeslide.domain.Discount;
import com.phamquyen.cafeslide.domain.Product;


@Repository
public interface DiscountRepository extends PagingAndSortingRepository<Discount, Long>{
	
	@Query("SELECT d FROM Discount d WHERE "
			+ "CONCAT(d.discountId, ' ', d.discountPercent, ' ', d.product, ' ', d.startDate, ' ', d.endDate, ' ', d.product.productName)"
			+ "LIKE %:keyword%")
	Page<Discount> findAll(Pageable page, String keyword);
	
	List<Discount> findByProduct(Product product);
}
