package com.phamquyen.furniticaShop.controller.admin;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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

import com.phamquyen.furniticaShop.domain.User;
import com.phamquyen.furniticaShop.dto.MyUserDetails;
import com.phamquyen.furniticaShop.dto.UserDto;
import com.phamquyen.furniticaShop.service.UserService;

@Controller
@RequestMapping("admin/profiles")
public class ProfileAdminController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping("")
	public String profile(@AuthenticationPrincipal MyUserDetails user, Model model) {
		User entity = userService.findById(user.getUserId()).get();
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(entity, userDto);
		userDto.setRoleName(entity.getRole().getRoleName());
		model.addAttribute("userDto", userDto); 
		return "admin/EditProfile";
	} 
	
	
	@PostMapping("update")
	public String update(UserDto userDto) {
		
		User user = userService.findById(userDto.getUserId()).get();
		
		user.setAddress(userDto.getAddress());
		user.setBirthday(userDto.getBirthday());
		user.setEmail(userDto.getEmail());
		user.setFullname(userDto.getFullname());
		user.setPhone(userDto.getPhone());
		user = userService.save(user);
		return "redirect:/login";
	}
	
	@GetMapping("updatePassword") 
	public String showUpdatePasswordForm() {
		
		return "admin/editPassword";     
	}
	
	@PostMapping("savePassword")
	public String savePassword(@RequestParam("password") String pasword, @AuthenticationPrincipal MyUserDetails user) {
		
		User entity = userService.findById(user.getUserId()).get();
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String newPass = encoder.encode(pasword);
		entity.setPassword(newPass);
		entity = userService.save(entity);
		return "redirect:/admin/home";   
	}
}
