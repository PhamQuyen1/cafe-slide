package com.phamquyen.furniticaShop.controller.admin;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.phamquyen.furniticaShop.domain.Feedback;
import com.phamquyen.furniticaShop.dto.FeedbackDto;
import com.phamquyen.furniticaShop.service.FeedbackService;

@Controller
@RequestMapping("/admin/feedbacks")
public class FeedbackAdminController {

	@Autowired
	FeedbackService feedbackService;

	@Autowired
	private JavaMailSender mailSender;

	@RequestMapping("")
	public String list(Model model) {
		String keyword = null;
		return paging(model, 1, "feedbackId", "desc", keyword);
	}

	@GetMapping("page/{page}")
	public String paging(Model model, @PathVariable("page") int page, @Param("sortField") String sortField,
			@Param("sortDir") String sortDir, @Param("keyword") String keyword) {

		List<FeedbackDto> listFeedbackDtos = feedbackService.listAll(page, keyword, sortField, sortDir);

		Page<Feedback> pages = feedbackService.paging(page, keyword, sortField, sortDir);
		Long totalItems = pages.getTotalElements();
		int totalPages = pages.getTotalPages();

		model.addAttribute("listFeedback", listFeedbackDtos);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", page);
		model.addAttribute("keyword", keyword);
		model.addAttribute("sortField", sortField); 
		model.addAttribute("sortDir", sortDir);

		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
		model.addAttribute("reverseSortDir", reverseSortDir);

		return "admin/feedback";
	}

	@GetMapping("reply/{feedbackId}")
	public ModelAndView showSendMailForm(ModelMap model, @PathVariable("feedbackId") Long feedbackId) {

		Optional<Feedback> feedback = feedbackService.findById(feedbackId);
		FeedbackDto feedbackDto = new FeedbackDto();
		
		if (feedback != null) {
			Feedback entity = feedback.get();
			BeanUtils.copyProperties(entity, feedbackDto);
			feedbackDto.setFeedbackUser(entity.getUser().getEmail());
			model.addAttribute("feedbackDto", feedbackDto);
			return new ModelAndView("admin/reply", model);
		}
		
		model.addAttribute("message", "Không tồn tại phản hồi");
		return new ModelAndView("admin/reply", model);
	}

	@PostMapping("reply")
	public ModelAndView sendMail(HttpServletRequest httpServletRequest, ModelMap model) {
		String to = (String) httpServletRequest.getParameter("feedbackUser");
		String subject = (String) httpServletRequest.getParameter("subject");
		String content = (String) httpServletRequest.getParameter("content");
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
  
		simpleMailMessage.setTo(to);
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText(content);
		
		mailSender.send(simpleMailMessage);
		
		model.addAttribute("message","Đã gửi mail phản hồi thành công");
		
		return new ModelAndView("forward:/admin/feedbacks", model); 
	}
	
	@GetMapping("delete/{feedbackId}")
	public ModelAndView deleteById(@PathVariable("feedbackId") Long feedbackId, ModelMap model) {
		
		Optional<Feedback> feedback = feedbackService.findById(feedbackId);
		
		if(feedback != null) {
			feedbackService.deleteById(feedbackId);
			model.addAttribute("message", "Đã xóa Phản hồi thành công");
		}else model.addAttribute("message", "Không tồn tại phản hồi");
		
		 
		return new ModelAndView("forward:/admin/feedbacks", model);
		
	}
	
}
