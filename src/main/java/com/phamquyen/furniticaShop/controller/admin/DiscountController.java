package com.phamquyen.furniticaShop.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.phamquyen.furniticaShop.domain.Discount;
import com.phamquyen.furniticaShop.domain.Product;
import com.phamquyen.furniticaShop.dto.DiscountDto;
import com.phamquyen.furniticaShop.service.DiscountService;
import com.phamquyen.furniticaShop.service.ProductService;

@Controller
@RequestMapping("admin/discounts")
public class DiscountController {
	
	@Autowired
	DiscountService discountService;
	
	@Autowired
	ProductService productService;
	
	
	@RequestMapping("")
	public String list(Model model) {
		String keyword = null;
		return paging(model, 1, "discountId", "desc", keyword);
	}

	@GetMapping("page/{page}")
	public String paging(Model model, @PathVariable("page") int page, @Param("sortField") String sortField,
			@Param("sortDir") String sortDir, @Param("keyword") String keyword) {

		List<DiscountDto> listDiscounts = discountService.listAll(page, keyword, sortField, sortDir);

		Page<Discount> pages = discountService.paging(page, keyword, sortField, sortDir);
		Long totalItems = pages.getTotalElements();
		int totalPages = pages.getTotalPages();

		model.addAttribute("listDiscounts", listDiscounts);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", page);
		model.addAttribute("keyword", keyword);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);

		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
		model.addAttribute("reverseSortDir", reverseSortDir);

		return "admin/discount";
	}
	
	@GetMapping("update/{discountId}")
	public ModelAndView update(ModelMap model, @PathVariable("discountId") Long discountId) {
		
		Optional<DiscountDto> discountDto = discountService.findById(discountId);
		
		if(discountDto != null) {
			DiscountDto discount = discountDto.get();
			model.addAttribute("discountDto", discount);
			model.addAttribute("edit", "Chỉnh sửa khuyến mãi");
			return new ModelAndView("admin/addOrEditDiscount", model);
		}
		model.addAttribute("message", "Không tồn tại khuyến mãi của sản phẩm");
		return new ModelAndView("admin/addOrEditDiscount", model); 
	}
	@PostMapping("/saveOrUpdate") 
	public ModelAndView saveOrUpdate(ModelMap model, DiscountDto discountDto) {
		
		Discount discount = new Discount();
		BeanUtils.copyProperties(discountDto, discount);
		Product product = productService.findByProductName(discountDto.getProductName());
		discount.setProduct(product); 
		discount = discountService.save(discount);
		model.addAttribute("message", "Đã cập nhập thành công");
		return new ModelAndView("forward:/admin/discounts", model);
	}
	
	@GetMapping("delete/{discountId}")  
	public ModelAndView delete(ModelMap model, @PathVariable("discountId") Long discountId) {
		
		Optional<DiscountDto> discountDto = discountService.findById(discountId);
		if(discountDto != null) {
			discountService.deleteById(discountId);
			model.addAttribute("message", "Đã xóa Khuyến mãi thành công");
		}else model.addAttribute("message", "Không tồn tại Khuyến mãi");
		
		return new ModelAndView("forward:/admin/discounts", model);
	}
	
	@GetMapping("add/{productId}")
	public ModelAndView addProduct(ModelMap model, @PathVariable("productId") Long productId) {
		DiscountDto discountDto = new DiscountDto();
		Optional<Product> entity = productService.findById(productId);
		
		if(entity.isPresent()) {
			Product product = entity.get();
			discountDto.setProductName(product.getProductName());
		}
		model.addAttribute("discountDto", discountDto);
		return new ModelAndView("admin/addOrEditDiscount", model);
	}
}
