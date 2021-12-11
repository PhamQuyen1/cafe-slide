package com.phamquyen.furniticaShop.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDto {
	
	private Long feedbackId;
	private String feedbackUser;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date feedbackDate;
	private String feedbackContent; 

}
