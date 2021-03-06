package com.phamquyen.cafeslide.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.phamquyen.cafeslide.domain.User;
import com.phamquyen.cafeslide.dto.MyUserDetails;
import com.phamquyen.cafeslide.repository.UserRepository;

public class UserDetailsServiceImpl 
	implements UserDetailsService {
	
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) 
			throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);
		
		if(user == null) 
			throw new UsernameNotFoundException("Không tồn tại email");
		return new MyUserDetails(user);    
	}

}
