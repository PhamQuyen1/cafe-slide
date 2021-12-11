package com.phamquyen.furniticaShop.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.phamquyen.furniticaShop.domain.User;
import com.phamquyen.furniticaShop.dto.UserDto;
import com.phamquyen.furniticaShop.repository.UserRepository;
import com.phamquyen.furniticaShop.service.UserService;
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;

	@Override
	public List<UserDto> listAll(int page, String keyword, String sortField, String sortDir) {
		Page<User> pages = paging(page, keyword, sortField, sortDir);
		List<User> listUser = pages.getContent();
		List<UserDto> listUserDto = new ArrayList<>();
		for (User user : listUser) {
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(user, userDto);
			userDto.setRoleName(user.getRole().getRoleName());
			listUserDto.add(userDto);
		}
		
		return listUserDto;
	}
	@Override
	public Page<User> paging(int page, String keyword, String sortField, String sortDir) {
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending(): sort.descending();
		Pageable pageable = PageRequest.of(page - 1, 4, sort);
		if (keyword != null)
			return userRepository.findAll(pageable, keyword);
		
		return userRepository.findAll(pageable); 
	}
	@Override
	public <S extends User> S save(S entity) {
		return userRepository.save(entity);
	}
	
	@Override
	public long count() {
		return userRepository.count();
	}
	@Override
	public void deleteById(Long id) {
		userRepository.deleteById(id);
	}
	@Override
	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}
	
}
