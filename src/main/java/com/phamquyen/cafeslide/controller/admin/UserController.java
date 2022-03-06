package com.phamquyen.cafeslide.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.phamquyen.cafeslide.domain.Role;
import com.phamquyen.cafeslide.domain.User;
import com.phamquyen.cafeslide.dto.MyUserDetails;
import com.phamquyen.cafeslide.dto.UserDto;
import com.phamquyen.cafeslide.service.RoleService;
import com.phamquyen.cafeslide.service.UserService;

@Controller
@RequestMapping("/admin/users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	RoleService roleService;
	
	@RequestMapping("")
	public String list(Model model) {
		String keyword = null;
		return paging(model, 1, "userId", "desc", keyword);
	}
	
	@GetMapping("page/{page}")
	public String paging(Model model, @PathVariable("page") int page, 
							@Param("sortField") String sortField,
							@Param("sortDir") String sortDir, 
							@Param("keyword") String keyword) {
		
		List<UserDto> listUsers = userService.listAll(page, keyword, sortField, sortDir);
		
		Page<User> pages = userService.paging(page, keyword, sortField, sortDir);
		Long totalItems = pages.getTotalElements();
		int totalPages = pages.getTotalPages(); 
		
		
		model.addAttribute("listUsers", listUsers);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", page); 
		model.addAttribute("keyword", keyword);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		
		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
		model.addAttribute("reverseSortDir", reverseSortDir);
		
		return "admin/user"; 
	}
	
	@GetMapping("delete/{userId}")
	public ModelAndView deleteById(@PathVariable("userId") Long userId, ModelMap model) {
		
		Optional<User> user = userService.findById(userId);
		
		if(user != null) {
			userService.deleteById(userId);
			model.addAttribute("message", "Đã xóa Người dùng thành công");
		}else model.addAttribute("message", "Không tồn tại người dùng");
		
		  
		return new ModelAndView("forward:/admin/feedbacks", model);
		
	}
	
	@GetMapping("update/{userId}")
	public ModelAndView update(ModelMap model, @PathVariable("userId") Long userId) {
		
		Optional<User> entity = userService.findById(userId);
		
		if(entity != null) {
			UserDto userDto = new UserDto();
			User user = entity.get();
			BeanUtils.copyProperties(user, userDto);
			userDto.setRoleName(user.getRole().getRoleName());
			model.addAttribute("userDto", userDto);
			model.addAttribute("edit", "Chỉnh sửa người dùng");
			return new ModelAndView("admin/addOrEditUser", model);
		}
		model.addAttribute("message", "Không tồn tại người dùng");
		return new ModelAndView("admin/addOrEditUser", model); 
	}
	@PostMapping("/saveOrUpdate") 
	public ModelAndView saveOrUpdate(ModelMap model, UserDto userDto) {
		
		Optional<User> entity = userService.findById(userDto.getUserId());
		if(entity.isPresent()) {
			User user = entity.get();
			user.setStatus(userDto.getStatus());
			Role role = roleService.findByRoleName(userDto.getRoleName());
			user.setRole(role);
			user = userService.save(user);
		}
		
		model.addAttribute("message", "Đã cập nhập thành công");
		return new ModelAndView("forward:/admin/users", model);
	}
	
}
