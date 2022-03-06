package com.phamquyen.cafeslide.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.phamquyen.cafeslide.domain.Category;
import com.phamquyen.cafeslide.dto.CategoryDto;
import com.phamquyen.cafeslide.repository.CategoryRepository;
import com.phamquyen.cafeslide.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public List<CategoryDto> listAll(int page, String keyword, String sortField, String sortDir) {
		Page<Category> pages = paging(page, keyword, sortField, sortDir);
		List<Category> listCategories = pages.getContent();
		List<CategoryDto> listCategoriesDto = new ArrayList<>();
		for (Category category : listCategories) {
			CategoryDto categoryDto = new CategoryDto();
			BeanUtils.copyProperties(category, categoryDto);
//			categoryDto.setTotalProduct(category.getProducts().size());
			listCategoriesDto.add(categoryDto);
		}

		return listCategoriesDto;
	}

	@Override
	public Iterable<Category> findAll() {
		return categoryRepository.findAll();
	}

	@Override
	public Page<Category> paging(int page, String keyword, String sortField, String sortDir) {
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("desc") ? sort.descending() : sort.ascending();
		Pageable pageable = PageRequest.of(page - 1, 2, sort);
		if (keyword != null)
			return categoryRepository.findAll(pageable, keyword);

		return categoryRepository.findAll(pageable);
	}

	@Override
	public Optional<Category> findById(Long id) {
		return categoryRepository.findById(id);
	}

	@Override
	public <S extends Category> S save(S entity) {
		return categoryRepository.save(entity);
	}

	@Override
	public void deleteById(Long id) {
		categoryRepository.deleteById(id);
	}

	@Override
	public Category findByCategoryName(String categoryName) {
		return categoryRepository.findByCategoryName(categoryName);
	}

}
