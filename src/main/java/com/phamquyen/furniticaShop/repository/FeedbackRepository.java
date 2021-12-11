package com.phamquyen.furniticaShop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.phamquyen.furniticaShop.domain.Feedback;

@Repository
public interface FeedbackRepository extends PagingAndSortingRepository<Feedback, Long>{

	@Query("SELECT f FROM Feedback f WHERE "
			+ "CONCAT(f.feedbackId, ' ', f.feedbackDate,' ', f.user.fullname)"
			+ "LIKE %:keyword%")
	Page<Feedback> findAll(Pageable page, String keyword);  
}
