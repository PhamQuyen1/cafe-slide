package com.phamquyen.furniticaShop.controller.authen;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.phamquyen.furniticaShop.domain.Role;
import com.phamquyen.furniticaShop.domain.User;
import com.phamquyen.furniticaShop.dto.UserDto;
import com.phamquyen.furniticaShop.service.RoleService;
import com.phamquyen.furniticaShop.service.UserService;

@Controller
@RequestMapping("/site/register")
public class RegisterController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	RoleService roleService;
	
	@GetMapping("")
	public String showFormRegister(Model model) {
		UserDto userDto = new UserDto();
		model.addAttribute("userDto", userDto);
		return "site/user-register"; 
	}
	
	@PostMapping("")	
	public String createAccount(RedirectAttributes redirectAttributes, UserDto userDto) {
		
		User user = new User();
		Role role = roleService.findByRoleName("CUSTOMER");
		BeanUtils.copyProperties(userDto, user);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String newPass = encoder.encode(userDto.getPassword());
		user.setPassword(newPass);
		user.setRole(role);
		user.setStatus(1);
		user = userService.save(user);
		redirectAttributes.addAttribute("message", "Đã tạo tài khoản thành công");
		return "redirect:/login"; 
	}

}
