package com.phamquyen.cafeslide.service.impl;

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

import com.phamquyen.cafeslide.domain.Feedback;
import com.phamquyen.cafeslide.dto.FeedbackDto;
import com.phamquyen.cafeslide.repository.FeedbackRepository;
import com.phamquyen.cafeslide.service.FeedbackService;



@Service
public class FeedbackServiceImpl implements FeedbackService {
	
	@Autowired
	FeedbackRepository feedbackRepository;
	
	@Override
	public List<FeedbackDto> listAll(int page, String keyword, String sortField, String sortDir) {
		Page<Feedback> pages = paging(page, keyword, sortField, sortDir);
		List<Feedback> listFeedbacks = pages.getContent();
		List<FeedbackDto> listFeedbackDto = new ArrayList<>();
		for (Feedback feedback : listFeedbacks) {
			FeedbackDto feedbackDto = new FeedbackDto();
			BeanUtils.copyProperties(feedback, feedbackDto);
			feedbackDto.setFeedbackUser(feedback.getUser().getFullname());
			listFeedbackDto.add(feedbackDto);
		}
	
		return listFeedbackDto;
	}
	@Override
	public <S extends Feedback> S save(S entity) {
		return feedbackRepository.save(entity);
	}
	@Override
	public long count() {
		return feedbackRepository.count();
	}
	@Override
	public Page<Feedback> paging(int page, String keyword, String sortField, String sortDir) {
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending(): sort.descending();
		Pageable pageable = PageRequest.of(page - 1, 2, sort);
		if (keyword != null)
			return feedbackRepository.findAll(pageable, keyword);
		
		return feedbackRepository.findAll(pageable); 
	}
	@Override
	public Optional<Feedback> findById(Long id) {
		return feedbackRepository.findById(id);
	}
	@Override
	public void deleteById(Long id) {
		feedbackRepository.deleteById(id);
	}
}
