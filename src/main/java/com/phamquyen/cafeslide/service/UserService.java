package com.phamquyen.cafeslide.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.phamquyen.cafeslide.domain.User;
import com.phamquyen.cafeslide.dto.UserDto;

public interface UserService {

	Page<User> paging(int page, String keyword, String sortField, String sortDir);

	List<UserDto> listAll(int page, String keyword, String sortField, String sortDir);

	<S extends User> S save(S entity);

	void deleteById(Long id);

	Optional<User> findById(Long id);

	long count();

	

}
