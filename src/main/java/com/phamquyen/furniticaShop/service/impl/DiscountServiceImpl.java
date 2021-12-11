package com.phamquyen.furniticaShop.service.impl;

import java.util.ArrayList;
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

import com.phamquyen.furniticaShop.domain.Discount;
import com.phamquyen.furniticaShop.domain.Product;
import com.phamquyen.furniticaShop.dto.DiscountDto;
import com.phamquyen.furniticaShop.dto.ProductDto;
import com.phamquyen.furniticaShop.repository.DiscountRepository;
import com.phamquyen.furniticaShop.service.DiscountService;

@Service
public class DiscountServiceImpl implements DiscountService {
	
	@Autowired
	DiscountRepository discountRepository;
	@Override
	public List<DiscountDto> listAll(int page, String keyword, String sortField, String sortDir) {
		Page<Discount> pages = paging(page, keyword, sortField, sortDir);
		List<Discount> listDiscounts = pages.getContent();
		List<DiscountDto> listDiscountDto = new ArrayList<>();
		for (Discount discount : listDiscounts) {
			DiscountDto discountDto = new DiscountDto();
			BeanUtils.copyProperties(discount, discountDto);
			discountDto.setProductName(discount.getProduct().getProductName());
			discountDto.setProductImageUrl(discount.getProduct().getImageUrl());
			listDiscountDto.add(discountDto); 
		}
	
		return listDiscountDto;
	} 
	@Override
	public Page<Discount> paging(int page, String keyword, String sortField, String sortDir) {
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("desc") ? sort.descending(): sort.ascending();
		Pageable pageable = PageRequest.of(page - 1, 2, sort);
		if (keyword != null)
			return discountRepository.findAll(pageable, keyword);
		
		return discountRepository.findAll(pageable); 
	}
	@Override
	public <S extends Discount> S save(S entity) {
		return discountRepository.save(entity);
	}
	@Override
	public Optional<DiscountDto> findById(Long id) {
		Optional<Discount> entity = discountRepository.findById(id);
		Discount discount = entity.get();
		DiscountDto discountDto = new DiscountDto(); 
		BeanUtils.copyProperties(discount, discountDto);
		discountDto.setProductName(discount.getProduct().getProductName());
		discountDto.setProductImageUrl(discount.getProduct().getImageUrl());
		
		return Optional.ofNullable(discountDto);
	}
	@Override
	public void deleteById(Long id) {  
		discountRepository.deleteById(id);
	}
	
	@Override
	public int haveDiscount(Product product) {
		List<Discount> listDiscounts = discountRepository.findByProduct(product);
		Date currentDate = new Date();
		Discount discount = new Discount();
		int price = product.getUnitPrice();
		if(!listDiscounts.isEmpty()) {
				for(int i = listDiscounts.size() - 1; i >= 0; i--) {
					Discount discountCheck = listDiscounts.get(listDiscounts.size()-1);
					if(currentDate.after(discountCheck.getStartDate()) && currentDate.before(discountCheck.getEndDate())) {
						BeanUtils.copyProperties(discountCheck, discount);
						price = discount.getProduct().getUnitPrice() - 
								discount.getProduct().getUnitPrice() * discount.getDiscountPercent() / 100;
						break;
					}
				}
		}
//		return Optional.ofNullable(discount);

		return price;
	}
}
