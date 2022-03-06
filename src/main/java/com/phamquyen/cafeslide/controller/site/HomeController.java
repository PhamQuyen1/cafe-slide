package com.phamquyen.cafeslide.controller.site;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.phamquyen.cafeslide.service.ProductService;

@Controller
public class HomeController {
	
	@Autowired
	ProductService productService;
	
	
	@RequestMapping(path= {"/site/home", "/", "/site"}, method = RequestMethod.GET)
	public String homepage(Model model) {
		model.addAttribute("listProduct",productService.listProductDesc().subList(0, 5));
		model.addAttribute("listProductDiscount",productService.listProductDiscount().subList(0, 5));
		return "site/index";  
	}  
	
	@RequestMapping(path= {"/site/aboutUs"}, method = RequestMethod.GET)
	public String aboutUs() {      
		return "site/about-us";   
	}

}
