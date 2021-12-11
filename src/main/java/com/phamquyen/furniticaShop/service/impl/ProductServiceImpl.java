package com.phamquyen.furniticaShop.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.phamquyen.furniticaShop.domain.Product;
import com.phamquyen.furniticaShop.dto.ProductDto;
import com.phamquyen.furniticaShop.repository.ProductRepository;
import com.phamquyen.furniticaShop.service.DiscountService;
import com.phamquyen.furniticaShop.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	DiscountService discountService;

	@Override
	public List<ProductDto> listAll(int page, String keyword, String sortField, String sortDir, int size) {
		Page<Product> pages = paging(page, keyword, sortField, sortDir, size);
		List<Product> listProducts = pages.getContent();
		List<ProductDto> listProductsDto = convertListProductDto(listProducts);

		return listProductsDto;
	}

	@Override
	public Page<Product> paging(int page, String keyword, String sortField, String sortDir, int size) {
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Pageable pageable = PageRequest.of(page - 1, size, sort);
		if (keyword != null)
			return productRepository.findAll(pageable, keyword);

		return productRepository.findAll(pageable);
	}

	@Override
	public Product findByProductName(String productName) {
		return productRepository.findByProductName(productName);
	}

	@Override
	public Optional<Product> findById(Long id) {
		return productRepository.findById(id);
	}

	@Override
	public <S extends Product> S save(S entity) {
		return productRepository.save(entity);
	}

	@Override
	public void deleteById(Long id) {
		productRepository.deleteById(id);
	}

	@Override
	public void delete(Product entity) {
		productRepository.delete(entity);
	}

	@Override
	public long count() {
		return productRepository.count();
	}

	@Override
	public List<ProductDto> listProductDesc() {
		Sort sort = Sort.by("productId");
		sort = sort.descending();
		List<Product> listProducts = (List<Product>) productRepository.findAll(sort);
		List<ProductDto> listProductsDto = convertListProductDto(listProducts);

		return listProductsDto;
	}

	@Override
	public List<ProductDto> listProductDiscount() {
		List<Product> listProduct = (List<Product>) productRepository.findAll();
		List<ProductDto> listProductDto = new ArrayList<>();
		for (Product product : listProduct) {
			ProductDto productDto = convertProductDto(product);
			if (productDto.getPrice() < productDto.getUnitPrice()) {
				listProductDto.add(productDto);
			}
		}
		return listProductDto;
	}

	@Override
	public Page<Product> pagingByCategory(int page, Long categoryId, String sortField, String sortDir, int size) {
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Pageable pageable = PageRequest.of(page - 1, size, sort);
		return productRepository.findAllByCategory(categoryId, pageable);
	}

	@Override
	public List<ProductDto> listAllByCategory(int page, Long categoryId, String sortField, String sortDir, int size) {
		Page<Product> pages = pagingByCategory(page, categoryId, sortField, sortDir, size);
		List<Product> listProducts = pages.getContent();
		List<ProductDto> listProductsDto = convertListProductDto(listProducts);

		return listProductsDto;
	}

	@Override
	public List<ProductDto> listProductBestSeller() {
		List<Product> listProducts = (List<Product>) productRepository.findAll();
		List<Product> listProductsSort = listProducts.stream().sorted((o1, o2) -> ((Integer) o2.getItems().stream().mapToInt(i -> i.getQuantity()).sum())
				.compareTo((Integer) o1.getItems().stream().mapToInt(i -> i.getQuantity()).sum())).collect(Collectors.toList());
//		List<Product> listProductsSort = listProducts.stream().sorted(Comparator.comparingInt(p->p.getItems().stream().mapToInt(i -> i.getQuantity()).sum())).collect(Collectors.toList());
		List<ProductDto> listProductsDto = convertListProductDto(listProductsSort);

		return listProductsDto;
	}

	@Override
	public ProductDto convertProductDto(Product product) {

		ProductDto productDto = new ProductDto();
		BeanUtils.copyProperties(product, productDto);
		productDto.setCategoryName(product.getCategory().getCategoryName());
		productDto.setImageUrl(product.getImageUrl());
		productDto.setPrice(discountService.haveDiscount(product));
		return productDto;
	}

	@Override
	public List<ProductDto> convertListProductDto(List<Product> listProducts) {

		List<ProductDto> listProductsDto = new ArrayList<>();

		for (Product product : listProducts) {
			ProductDto productDto = convertProductDto(product);
			listProductsDto.add(productDto);
		}
		return listProductsDto;
	}

}
