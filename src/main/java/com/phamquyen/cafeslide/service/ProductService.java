package com.phamquyen.cafeslide.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.phamquyen.cafeslide.domain.Product;
import com.phamquyen.cafeslide.dto.ProductDto;

public interface ProductService {

	


	Product findByProductName(String productName);

	Optional<Product> findById(Long id);

	<S extends Product> S save(S entity);

	void deleteById(Long id);

	void delete(Product entity);

	long count();

	List<ProductDto> listProductDesc();

	List<ProductDto> listProductDiscount();

//	Page<Product> listAll(int pages, String sortField, String sortDir, String keyword, int size);

	Page<Product> pagingByCategory(int page, Long categoryId, String sortField, String sortDir, int size);

	List<ProductDto> listAllByCategory(int page, Long categoryId, String sortField, String sortDir, int size);

	List<ProductDto> listProductBestSeller();

	Page<Product> paging(int page, String keyword, String sortField, String sortDir, int size);

	List<ProductDto> listAll(int page, String keyword, String sortField, String sortDir, int size);

	List<ProductDto> convertListProductDto(List<Product> listProducts);

	ProductDto convertProductDto(Product product);

}
