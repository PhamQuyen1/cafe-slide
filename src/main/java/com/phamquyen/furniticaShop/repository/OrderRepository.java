package com.phamquyen.furniticaShop.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.phamquyen.furniticaShop.domain.Order;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, Long>{
	
	@Query("SELECT o FROM Order o WHERE "
			+ "CONCAT(o.orderId, ' ', o.orderDate,' ', o.user.fullname)"
			+ "LIKE %:keyword%")
	Page<Order> findAll(Pageable page, String keyword);
	
	List<Order> findByOrderDateAfter(Date orderDate);
	
	Order findTopByOrderByOrderIdDesc();
}
