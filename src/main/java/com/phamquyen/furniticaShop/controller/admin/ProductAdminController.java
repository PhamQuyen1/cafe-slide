package com.phamquyen.furniticaShop.controller.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.phamquyen.furniticaShop.domain.Category;
import com.phamquyen.furniticaShop.domain.Feedback;
import com.phamquyen.furniticaShop.domain.Product;
import com.phamquyen.furniticaShop.dto.FeedbackDto;
import com.phamquyen.furniticaShop.dto.ProductDto;
import com.phamquyen.furniticaShop.service.CategoryService;
import com.phamquyen.furniticaShop.service.ProductService;

@Controller
@RequestMapping("/admin/products")
public class ProductAdminController {

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
	public String paging(Model model, @PathVariable("page") int page, @Param("sortField") String sortField,
			@Param("sortDir") String sortDir, @Param("keyword") String keyword) {

		List<ProductDto> listProductsDto = productService.listAll(page, keyword, sortField, sortDir, 3);

		Page<Product> pages = productService.paging(page, keyword, sortField, sortDir, 3);
		Long totalItems = pages.getTotalElements();
		int totalPages = pages.getTotalPages();

		model.addAttribute("listProductsDto", listProductsDto);
		model.addAttribute("totalItems", totalItems); 
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", page);
		model.addAttribute("keyword", keyword);
		model.addAttribute("sortField", sortField); 
		model.addAttribute("sortDir", sortDir);

		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
		model.addAttribute("reverseSortDir", reverseSortDir);

		return "admin/product";
	}

	@GetMapping("add")
	public String addProduct(Model model) {
		model.addAttribute("productDto", new ProductDto());
		model.addAttribute("categories", categoryService.findAll());
		return "admin/addOrEditProduct";
	}

	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model, ProductDto dto,
			@RequestParam("fileImage") MultipartFile multipartFile) throws IOException {
		
		
		Product product = new Product();
		BeanUtils.copyProperties(dto, product);
		product.setCategory(categoryService.findByCategoryName(dto.getCategoryName()));
		Product p = new Product();
		if(!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			product.setImageUrl(fileName);
			
			String uploadDir = "./upload";
			Path uploadPath = Paths.get(uploadDir);
			
			if(!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			} 
			
			try {
				InputStream inputStream = multipartFile.getInputStream();
				Path filePath = uploadPath.resolve(fileName);
				Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				// TODO: handle exception
				throw new IOException("Không the lưu file" + fileName);
			}
		}else {
			if(product.getProductId() != null) {
				
				p = productService.findById(product.getProductId()).get();
				String path = p.getImageUrl();
				path = path.substring(8);
				product.setImageUrl(path);
			}else {
				product.setImageUrl("default.png");
			}
		}
		product = productService.save(product);
		model.addAttribute("message", "Đã thêm mới sản phẩm thành công");
		return new ModelAndView("forward:/admin/products", model);
	}
	
	@GetMapping("update/{productId}")  
	public ModelAndView updateProduct(ModelMap model, @PathVariable("productId") Long productId) {
		
		Product product = productService.findById(productId).get();
		ProductDto productDto = new ProductDto();
		BeanUtils.copyProperties(product, productDto);
		
		model.addAttribute("categories", categoryService.findAll());
		model.addAttribute("productDto", productDto);
		model.addAttribute("update", true);   
		return new ModelAndView("admin/addOrEditProduct", model);
	}
	
	@GetMapping("delete/{productId}") 
	public String delateProduct(Model model, @PathVariable("productId") Long productId) {
	          
		Product product = productService.findById(productId).get();
 
		try {
			if(!product.getImageUrl().equals("/upload/default.png")) {
				
				String filePath = "."+product.getImageUrl();
				if(Files.exists(Paths.get(filePath))) {
					Files.delete(Paths.get(filePath));
				}
			}
			productService.delete(product);
			model.addAttribute("message", "Đã xóa sản phẩm");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		}
		
		
		return "forward:/admin/products"; 
	}
}
