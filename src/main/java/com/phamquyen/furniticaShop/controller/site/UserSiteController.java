package com.phamquyen.furniticaShop.controller.site;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.phamquyen.furniticaShop.domain.Order;
import com.phamquyen.furniticaShop.domain.User;
import com.phamquyen.furniticaShop.dto.MyUserDetails;
import com.phamquyen.furniticaShop.dto.OrderDto;
import com.phamquyen.furniticaShop.service.OrderService;
import com.phamquyen.furniticaShop.service.UserService;

@Controller
@RequestMapping("/site/users")
public class UserSiteController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	OrderService orderService;
	
	@RequestMapping("")
	public String viewInfo(@AuthenticationPrincipal MyUserDetails user, Model model) {
		
		List<Order> listOrders = userService.findById(user.getUserId()).get().getOrders();
		List<OrderDto> listOrderDto = new ArrayList<>();
		for (Order order : listOrders) {
			OrderDto orderDto = new OrderDto();
			BeanUtils.copyProperties(order, orderDto);
			int total = order.getItems().stream().mapToInt(o->o.subTotal()).sum();
			orderDto.setOrderUser(order.getUser().getFullname()); 
			orderDto.setTotal(total); 
			listOrderDto.add(orderDto);  
		}
		Collections.reverse(listOrderDto);
		model.addAttribute("orders", listOrderDto);
		model.addAttribute("orderSize", listOrderDto.size());
		return "site/user-acount";   
	}
	
	@GetMapping("update")
	public String showUpdateInfoForm() {
		
		return "site/user-update-info"; 
	}
	
	@PostMapping("update")
	public ModelAndView updateInfo(User userDto, ModelMap model, @AuthenticationPrincipal MyUserDetails user) {
		
		User entity = userService.findById(user.getUserId()).get();
		entity.setAddress(userDto.getAddress());
		entity.setBirthday(userDto.getBirthday());
		entity.setEmail(userDto.getEmail());
		entity.setFullname(userDto.getFullname());
		entity.setPhone(userDto.getPhone());
		
		entity = userService.save(entity);
		
		model.addAttribute("message", "Đã cập nhập thông tin thành công");
		
		return new ModelAndView("forward:/site/users", model);  
		
	}
	@GetMapping("resetPassword")
	public String showUpdatePasswordForm() {
		
		return "site/user-reset-password";     
	}
	
	@PostMapping("resetPassword")
	public ModelAndView savePassword(@RequestParam("password") String pasword, @AuthenticationPrincipal MyUserDetails user, ModelMap model) {
		
		User entity = userService.findById(user.getUserId()).get();
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String newPass = encoder.encode(pasword);
		entity.setPassword(newPass);
		entity = userService.save(entity);
		model.addAttribute("message", "Đã cập nhập mật khẩu thành công");
		return new ModelAndView("forward:/site/users", model);       
	}
}
