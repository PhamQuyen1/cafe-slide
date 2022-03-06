package com.phamquyen.cafeslide.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.phamquyen.cafeslide.domain.Order;
import com.phamquyen.cafeslide.dto.OrderDto;
import com.phamquyen.cafeslide.repository.OrderRepository;
import com.phamquyen.cafeslide.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	OrderRepository orderRepository;
	
	@Override
	public List<OrderDto> listAll(int page, String keyword, String sortField, String sortDir) {
		Page<Order> pages = paging(page, keyword, sortField, sortDir);
		List<Order> listOrders = pages.getContent();
		List<OrderDto> listOrderDto = new ArrayList<>();
		for (Order order : listOrders) {
			OrderDto orderDto = new OrderDto();
			BeanUtils.copyProperties(order, orderDto);
			int total = order.getItems().stream().mapToInt(o->o.subTotal()).sum();
			orderDto.setOrderUser(order.getUser().getFullname()); 
			orderDto.setTotal(total); 
			listOrderDto.add(orderDto);
		}
	
		return listOrderDto;
	}
	@Override
	public Page<Order> paging(int page, String keyword, String sortField, String sortDir) {
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending(): sort.descending();
		Pageable pageable = PageRequest.of(page - 1, 2, sort);
		if (keyword != null)
			return orderRepository.findAll(pageable, keyword);
		
		return orderRepository.findAll(pageable); 
	}
	@Override
	public Optional<Order> findById(Long id) {
		return orderRepository.findById(id);
	}
	@Override
	public <S extends Order> S save(S entity) {
		return orderRepository.save(entity);
	}
	@Override
	public long count() {
		return orderRepository.count();
	}
	@Override
	public Iterable<Order> findAll() {
		return orderRepository.findAll();
	}
	@Override
	public Order findTopByOrderByOrderIdDesc() {
		return orderRepository.findTopByOrderByOrderIdDesc();
	}
	

}
