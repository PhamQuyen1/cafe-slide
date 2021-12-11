package com.phamquyen.furniticaShop.controller.authen;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	
	@GetMapping("/logout")
	public String logout() {
		return "site/user-login";
	}
	@GetMapping("/login")
	public String showLoginForm() {
		return "site/user-login";      
	}
	@GetMapping("/403")
	public String notPermission() {
		return "admin/403";      
	}

}
