package com.phamquyen.cafeslide.controller.site;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.phamquyen.cafeslide.domain.Item;
import com.phamquyen.cafeslide.domain.User;
import com.phamquyen.cafeslide.dto.MyUserDetails;
import com.phamquyen.cafeslide.service.CategoryService;
import com.phamquyen.cafeslide.service.ShoppingCartService;
import com.phamquyen.cafeslide.service.UserService;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

	@Autowired
	CategoryService categoryService;

	@Autowired
	ShoppingCartService shoppingCartService;
 
	@Autowired 
	UserService userService;     
 
	@ModelAttribute     
	public void addAttributes(Model model, @AuthenticationPrincipal MyUserDetails user) {
		model.addAttribute("categories", categoryService.findAll());
		List<Item> cartItems = new ArrayList<>(shoppingCartService.getAll());
		model.addAttribute("cartItems", cartItems);
		model.addAttribute("totalCartItems", shoppingCartService.getTotalItem());
		model.addAttribute("totalAmount", shoppingCartService.getTotalAmount());
		if(user != null) {
			User entity = userService.findById(user.getUserId()).get();
  
			model.addAttribute("userDto", entity);                                  
		} 
		     
	}

}
