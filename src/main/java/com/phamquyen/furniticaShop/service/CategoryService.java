package com.phamquyen.furniticaShop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.phamquyen.furniticaShop.domain.Category;
import com.phamquyen.furniticaShop.dto.CategoryDto;


public interface CategoryService {

	Optional<Category> findById(Long id);

	<S extends Category> S save(S entity);

	void deleteById(Long id);

	Page<Category> paging(int page, String keyword, String sortField, String sortDir);

	List<CategoryDto> listAll(int page, String keyword, String sortField, String sortDir);

	Iterable<Category> findAll();

	Category findByCategoryName(String categoryName);

}
