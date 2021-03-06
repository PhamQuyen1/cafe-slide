package com.phamquyen.cafeslide.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.phamquyen.cafeslide.domain.Discount;
import com.phamquyen.cafeslide.domain.Product;
import com.phamquyen.cafeslide.dto.DiscountDto;

public interface DiscountService {

	Page<Discount> paging(int page, String keyword, String sortField, String sortDir);

	List<DiscountDto> listAll(int page, String keyword, String sortField, String sortDir);

	void deleteById(Long id);

	<S extends Discount> S save(S entity);

	Optional<DiscountDto> findById(Long id);


	int haveDiscount(Product product);

}
