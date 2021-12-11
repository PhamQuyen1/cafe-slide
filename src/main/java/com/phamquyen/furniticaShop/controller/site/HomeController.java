package com.phamquyen.furniticaShop.controller.site;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.phamquyen.furniticaShop.service.ProductService;

@Controller
@RequestMapping("/site")
public class HomeController {
	
	@Autowired
	ProductService productService;
	
	@GetMapping("home")
	public String homepage(Model model) {
		model.addAttribute("listProduct",productService.listProductDesc().subList(0, 5));
		model.addAttribute("listProductDiscount",productService.listProductDiscount().subList(0, 5));
		return "site/index";  
	}  
	@GetMapping("aboutUs")
	public String aboutUs() {
		return "site/about-us";
	}

}
