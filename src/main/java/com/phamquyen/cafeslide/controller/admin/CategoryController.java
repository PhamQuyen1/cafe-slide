package com.phamquyen.cafeslide.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.phamquyen.cafeslide.domain.Category;
import com.phamquyen.cafeslide.domain.User;
import com.phamquyen.cafeslide.dto.CategoryDto;
import com.phamquyen.cafeslide.dto.MyUserDetails;
import com.phamquyen.cafeslide.dto.UserDto;
import com.phamquyen.cafeslide.service.CategoryService;
import com.phamquyen.cafeslide.service.UserService;



@Controller
@RequestMapping("/admin/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService; 
	


	@RequestMapping("")
	public String list(Model model) {
		String keyword = null;
		return paging(model, 1, "categoryId", "desc", keyword);
	}

	@GetMapping("page/{page}")
	public String paging(Model model, @PathVariable("page") int page, @Param("sortField") String sortField,
			@Param("sortDir") String sortDir, @Param("keyword") String keyword) {

		List<CategoryDto> listCategoriesDto = categoryService.listAll(page, keyword, sortField, sortDir);

		Page<Category> pages = categoryService.paging(page, keyword, sortField, sortDir);
		Long totalItems = pages.getTotalElements();
		int totalPages = pages.getTotalPages();
		model.addAttribute("listCategoriesDto", listCategoriesDto);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", page); 
		model.addAttribute("keyword", keyword);
		model.addAttribute("sortField", sortField);     
		model.addAttribute("sortDir", sortDir);   

		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
		model.addAttribute("reverseSortDir", reverseSortDir); 
   
		return "admin/category";
	}

	@GetMapping("add")
	public String add(Model model) {
		CategoryDto category = new CategoryDto();
		model.addAttribute("category", category);
		return "admin/addOrEditCategory";
	}

	@GetMapping("update/{categoryId}")
	public ModelAndView update(ModelMap model, 
			@PathVariable("categoryId") Long categoryId) {

		Optional<Category> category = categoryService.findById(categoryId);
		CategoryDto dto = new CategoryDto();
		if (category != null) {
			Category entity = category.get();
			BeanUtils.copyProperties(entity, dto);
			model.addAttribute("edit", "Chỉnh sửa danh mục sản phẩm");
			model.addAttribute("category", dto);

			return new ModelAndView("admin/addOrEditCategory", model);
		} 
		model.addAttribute("message", "Không tồn tại danh mục");
		return new ModelAndView("admin/addOrEditCategory", model);
	} 

	@GetMapping("delete/{categoryId}")
	public String delete(Model model, @PathVariable("categoryId") Long categoryId) {

		Optional<Category> category = categoryService.findById(categoryId);

		if (category != null) {
			categoryService.deleteById(categoryId);
			model.addAttribute("message", "Đã xóa danh mục");

		} else
			model.addAttribute("message", "Không tồn tại danh mục");
		return "forward:/admin/categories";
	}

	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model, CategoryDto dto) {
		
		Category category = new Category();
		BeanUtils.copyProperties(dto, category);
		category = categoryService.save(category);

		model.addAttribute("message", "Danh mục đã được lưu"); 

		return new ModelAndView("forward:/admin/categories", model);
	}

}
