package com.phamquyen.furniticaShop.controller.site;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.phamquyen.furniticaShop.domain.Product;
import com.phamquyen.furniticaShop.dto.ProductDto;
import com.phamquyen.furniticaShop.service.CategoryService;
import com.phamquyen.furniticaShop.service.ProductService;

@Controller
@RequestMapping("site/products")
public class ProductSiteController {
	
	@Autowired
	ProductService productService;
	
	@Autowired
	CategoryService categoryService;
	
	
	@RequestMapping("")
	public String list(Model model) {
		String keyword = null;
		return paging(model, 1, "productId", "desc", keyword);
	}

	@GetMapping("page/{page}")
	public String paging(Model model, 
						@PathVariable("page") int page, 
						@Param("sortField") String sortField,
						@Param("sortDir") String sortDir, 
						@Param("keyword") String keyword) {
 
		List<ProductDto> listProductsDto = productService.listAll(page, keyword, sortField, sortDir, 9);

		Page<Product> pages = productService.paging(page, keyword, sortField, sortDir, 9);
		Long totalItems = pages.getTotalElements();
		int totalPages = pages.getTotalPages();
    
		model.addAttribute("listProducts", listProductsDto);
		model.addAttribute("totalItems", totalItems); 
		model.addAttribute("totalPages", totalPages); 
		model.addAttribute("currentPage", page);
		model.addAttribute("keyword", keyword);
		model.addAttribute("sortField", sortField); 
		model.addAttribute("sortDir", sortDir);
		List<ProductDto> listProductsBestSeller = productService.listProductBestSeller().subList(0, 4);
		model.addAttribute("listProductsBestSeller", listProductsBestSeller);
		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
		model.addAttribute("reverseSortDir", reverseSortDir);

		return "site/product";
	}
	@RequestMapping("/productDetail")
	public String viewDetail() {
		 
		return "site/product-detail";    
	}
	@RequestMapping("category/{categoryId}")       
	public String listByCategory(@PathVariable("categoryId") Long categoryId, Model model) {
		
		return pagingByCategory(model, 1, "productId", "desc", categoryId);  
	}
	@GetMapping("category/page/{categoryId}")
	public String pagingByCategory(Model model, 
									@PathVariable("page") int page, 
									@Param("sortField") String sortField,
									@Param("sortDir") String sortDir, 
									@PathVariable("categoryId") Long categoryId) {

		List<ProductDto> listProducts = productService.listAllByCategory(page, categoryId, "productId", "desc", 9);
		
		Page<Product> pages = productService.pagingByCategory(page, categoryId, "productId", "desc", 9);
		Long totalItems = pages.getTotalElements();
		int totalPages = pages.getTotalPages();

		model.addAttribute("listProducts", listProducts);
		model.addAttribute("totalItems", totalItems); 
		model.addAttribute("totalPages", totalPages); 
		model.addAttribute("currentPage", page);
//		model.addAttribute("keyword", categoryId);  
		model.addAttribute("sortField", sortField);  
		model.addAttribute("sortDir", sortDir);
		List<ProductDto> listProductsBestSeller = productService.listProductBestSeller().subList(0, 4);
		model.addAttribute("listProductsBestSeller", listProductsBestSeller);
		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
		model.addAttribute("reverseSortDir", reverseSortDir);

		return "site/product";         
	}
	
	@GetMapping("productDetail/{productId}")
	public String viewProductDetail(@PathVariable("productId") Long productId, Model model) {
		
		Product product = productService.findById(productId).get();
		
		List<ProductDto> listProductsBestSeller = productService.listProductBestSeller().subList(0, 4);
		List<Product> relatedProducts = product.getCategory().getProducts();
		
		ProductDto productDto = productService.convertProductDto(product);
		List<ProductDto> relatedProductsDto = productService.convertListProductDto(relatedProducts);
		relatedProductsDto.remove(productDto);
		model.addAttribute("listProductsBestSeller", listProductsBestSeller);
		model.addAttribute("productDto", productDto);  
		model.addAttribute("relatedProductsDto", relatedProductsDto);
		return "site/product-detail";      
	}

}
