package com.phamquyen.furniticaShop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.phamquyen.furniticaShop.domain.Order;
import com.phamquyen.furniticaShop.dto.OrderDto;

public interface OrderService {

	Page<Order> paging(int page, String keyword, String sortField, String sortDir);

	List<OrderDto> listAll(int page, String keyword, String sortField, String sortDir);

	Optional<Order> findById(Long id);

	<S extends Order> S save(S entity);

	long count();

	Iterable<Order> findAll();

	Order findTopByOrderByOrderIdDesc();

}
