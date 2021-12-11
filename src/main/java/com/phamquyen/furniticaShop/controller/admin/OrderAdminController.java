package com.phamquyen.furniticaShop.controller.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.swing.text.html.FormSubmitEvent.MethodType;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
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
import com.phamquyen.furniticaShop.domain.Item;
import com.phamquyen.furniticaShop.domain.Order;
import com.phamquyen.furniticaShop.domain.Product;
import com.phamquyen.furniticaShop.dto.FeedbackDto;
import com.phamquyen.furniticaShop.dto.ItemDto;
import com.phamquyen.furniticaShop.dto.OrderDto;
import com.phamquyen.furniticaShop.service.OrderService;

@Controller
@RequestMapping("/admin/orders")
public class OrderAdminController {
	
	@Autowired
	OrderService orderService;
	
	@RequestMapping("")
	public String list(Model model) {
		String keyword = null;
		return paging(model, 1, "orderDate", "desc", keyword);
	}
	
	@GetMapping("page/{page}")
	public String paging(Model model, @PathVariable("page") int page, 
							@Param("sortField") String sortField,
							@Param("sortDir") String sortDir,
							@Param("keyword") String keyword) {
		
		List<OrderDto> listOrderDtos = orderService.listAll(page, keyword, sortField, sortDir);
		
		Page<Order> pages = orderService.paging(page, keyword, sortField, sortDir);
		Long totalItems = pages.getTotalElements();
		int totalPages = pages.getTotalPages();
		
		
		model.addAttribute("listOrders", listOrderDtos);
		model.addAttribute("totalItems", totalItems);   
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", page);
		model.addAttribute("keyword", keyword);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		
		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
		model.addAttribute("reverseSortDir", reverseSortDir);
		
		return "admin/order"; 
	}
	
	@GetMapping("orderDetail/{orderId}")
	public ModelAndView view(ModelMap model,@PathVariable("orderId") Long orderId) {
		
		Optional<Order> entity = orderService.findById(orderId);
		
		if(entity.isPresent()) {
			Order order = entity.get();
			OrderDto orderDto = new OrderDto();
			BeanUtils.copyProperties(order, orderDto);
			
			int total = order.getItems().stream().mapToInt(o->o.subTotal()).sum();
			total += 50000;
			List<Item> items = order.getItems();
			List<ItemDto> listItemDto = new ArrayList<ItemDto>();
			for(Item item : items) {
				ItemDto itemDto = new ItemDto();
				BeanUtils.copyProperties(item, itemDto);
				listItemDto.add(itemDto);
			}
			orderDto.setOrderUser(order.getUser().getFullname());
			orderDto.setTotal(total);
			model.addAttribute("listItemDto", listItemDto);   
			model.addAttribute("orderDto", orderDto);  
		}
		
		return new ModelAndView("admin/orderdetail", model);  
	}
	@RequestMapping(value = "orderDetail/update/{orderId}",method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView update(ModelMap model, @PathVariable("orderId") Long orderId, @Param("status") String status) {
		
		Optional<Order> entity = orderService.findById(orderId);
		if(entity.isPresent()) {
			Order order = entity.get();
			String OldStatus = order.getOrderStatus();
			
			if(OldStatus.equals("Đặt hàng") || OldStatus.equals("Không duyệt")) {
				if(!status.equals("Không duyệt")) {
					List<Item> listItems = order.getItems();
					for (Item item : listItems) {
						Product product = item.getProduct();
						product.setQuantity(product.getQuantity() - item.getQuantity());
					}
				}
			}
			if(OldStatus.equals("Đã duyệt") || OldStatus.equals("Đang vận chuyển") || OldStatus.equals("Đã giao")) {
				if(status.equals("Không duyệt")) {
					List<Item> listItems = order.getItems();
					for (Item item : listItems) {           
						Product product = item.getProduct();
						product.setQuantity(product.getQuantity() + item.getQuantity());
					}
				}
			}
			order.setOrderStatus(status); 
			order = orderService.save(order);
			model.addAttribute("message", "Đã cập nhập đơn hàng");
		}  
		
		return new ModelAndView("forward:/admin/orders", model);
	}
}
