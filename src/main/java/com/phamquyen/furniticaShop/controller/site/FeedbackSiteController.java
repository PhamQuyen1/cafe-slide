package com.phamquyen.furniticaShop.controller.site;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.phamquyen.furniticaShop.domain.Feedback;
import com.phamquyen.furniticaShop.domain.User;
import com.phamquyen.furniticaShop.dto.FeedbackDto;
import com.phamquyen.furniticaShop.dto.MyUserDetails;
import com.phamquyen.furniticaShop.service.FeedbackService;
import com.phamquyen.furniticaShop.service.UserService;

@Controller
@RequestMapping("/site/feedbacks")
public class FeedbackSiteController {
	
	@Autowired 
	UserService userService;
	
	@Autowired
	FeedbackService feedbackService;
	
	@RequestMapping("")
	public String feedback(Model model, @AuthenticationPrincipal MyUserDetails myUserDetails) {
		
		User user = userService.findById(myUserDetails.getUserId()).get();
		Feedback feedback = new Feedback();
		feedback.setUser(user);
		FeedbackDto feedbackDto = new FeedbackDto();
		BeanUtils.copyProperties(feedback, feedbackDto);
		feedbackDto.setFeedbackUser(feedback.getUser().getFullname());
		model.addAttribute("feedbackDto", feedbackDto);
		return "site/feedback";    
	} 
	
	@PostMapping("send")
	public ModelAndView save(ModelMap model, FeedbackDto feedbackDto, @AuthenticationPrincipal MyUserDetails myUserDetails) {
			
		User user = userService.findById(myUserDetails.getUserId()).get();
		Feedback feedback = new Feedback();
		BeanUtils.copyProperties(feedbackDto, feedback);
		feedback.setUser(user);
		feedback.setFeedbackDate(new Date());
		feedback = feedbackService.save(feedback);
		model.addAttribute("message", "Đã gửi phản hồi thành công");
		return new ModelAndView("forward:/site/feedbacks", model);          
	}

}
