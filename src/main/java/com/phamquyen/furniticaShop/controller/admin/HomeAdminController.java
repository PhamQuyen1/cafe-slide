package com.phamquyen.furniticaShop.controller.admin;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.phamquyen.furniticaShop.domain.User;
import com.phamquyen.furniticaShop.dto.MyUserDetails;
import com.phamquyen.furniticaShop.dto.UserDto;
import com.phamquyen.furniticaShop.service.DiscountService;
import com.phamquyen.furniticaShop.service.FeedbackService;
import com.phamquyen.furniticaShop.service.OrderService;
import com.phamquyen.furniticaShop.service.ProductService;
import com.phamquyen.furniticaShop.service.UserService;

@Controller
@RequestMapping("/admin/home")
public class HomeAdminController {
	
	@Autowired
	ProductService productService;
	
	@Autowired 
	UserService userService;
	
	@Autowired
	DiscountService discountService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	FeedbackService feedbackService;
	
	@GetMapping("")
	public String index(Model model, @AuthenticationPrincipal MyUserDetails user) {
		
		User userEntity = userService.findById(user.getUserId()).get();
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userEntity, userDto);
		userDto.setRoleName(userEntity.getRole().getRoleName());
		
		long totalProduct = productService.count();
		long totalFeedback = feedbackService.count();
		long totalUser = userService.count(); 
		model.addAttribute("userDto", userDto);
		model.addAttribute("totalProducts", totalProduct);
		model.addAttribute("totalFeedbacks", totalFeedback);
		model.addAttribute("totalUsers", totalUser);
		model.addAttribute("totalOrdersInCurrentMonth", orderService.count());
		return "admin/index";  
	}
  
}
