package com.phamquyen.cafeslide.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.phamquyen.cafeslide.domain.Feedback;
import com.phamquyen.cafeslide.dto.FeedbackDto;

public interface FeedbackService {

	Page<Feedback> paging(int page, String keyword, String sortField, String sortDir);

	List<FeedbackDto> listAll(int page, String keyword, String sortField, String sortDir);

	Optional<Feedback> findById(Long id);

	void deleteById(Long id);

	long count();

	<S extends Feedback> S save(S entity);

}
