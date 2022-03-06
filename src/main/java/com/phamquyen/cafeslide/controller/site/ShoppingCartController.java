package com.phamquyen.cafeslide.controller.site;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.phamquyen.cafeslide.domain.Item;
import com.phamquyen.cafeslide.domain.Order;
import com.phamquyen.cafeslide.domain.Product;
import com.phamquyen.cafeslide.domain.User;
import com.phamquyen.cafeslide.dto.MyUserDetails;
import com.phamquyen.cafeslide.service.DiscountService;
import com.phamquyen.cafeslide.service.ItemService;
import com.phamquyen.cafeslide.service.OrderService;
import com.phamquyen.cafeslide.service.ProductService;
import com.phamquyen.cafeslide.service.ShoppingCartService;
import com.phamquyen.cafeslide.service.UserService;

@Controller
@RequestMapping("/site/shoppingCart")
public class ShoppingCartController {
	
	@Autowired
	ShoppingCartService shoppingCartService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	DiscountService discountService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	ItemService itemService;
	
	@RequestMapping("")
	public String viewCart(Model model) {
//		List<Item> cartItems = new ArrayList<>(shoppingCartService.getAll());
//		model.addAttribute("cartItems", cartItems);
//		model.addAttribute("totalCartItems", cartItems.size());
//		model.addAttribute("totalAmount", shoppingCartService.getTotalAmount());
		return "site/product-cart";
	}   
	
	@PostMapping("addToCart")
	public String addToCart(@RequestParam("quantity") int quantity, @RequestParam("productId") Long productId) {
		Product product = productService.findById(productId).get();
		Item item = new Item();
		item.setProduct(product); 
		item.setQuantity(quantity); 
		item.setPrice(discountService.haveDiscount(product));
		shoppingCartService.addToCart(item);
		
		return "redirect:/site/shoppingCart";      
	} 
	
	@GetMapping("delete/{productId}")
	public ModelAndView deleteCartItem(@PathVariable("productId") Long productId, ModelMap model) {
		shoppingCartService.delete(productId);
		model.addAttribute("message", "Đã xóa khỏi giỏ hàng thành công");
		return new ModelAndView("forward:/site/shoppingCart", model);
	}
	            
	@PostMapping("update")
	public String update(@RequestParam("quantity") int quantity, @RequestParam("productId") Long productId) {
		shoppingCartService.update(productId, quantity);
		
		return "redirect:/site/shoppingCart";              
	} 
	 
	@GetMapping("checkout")
	public String viewCheckout(@AuthenticationPrincipal MyUserDetails user, Model model) {
		
		User entity = userService.findById(user.getUserId()).get();
		Order order = new Order();
		order.setUser(entity);
		order.setOrderAddress(entity.getAddress());
		model.addAttribute("order" , order);
		return "site/checkout";  
	}
	
	@PostMapping("checkout")
	public String successCheckout(Model model, Order oder, @AuthenticationPrincipal MyUserDetails user) {
		Order entity = new Order();
		BeanUtils.copyProperties(oder, entity);
		entity.setOrderDate(new Date());
		List<Item> cartItems = new ArrayList<>(shoppingCartService.getAll());
		
//		cartItems = itemService.saveAll(cartItems);
//		entity.setItems(cartItems);
		entity.setUser(userService.findById(user.getUserId()).get());
		entity.setOrderStatus("Đặt hàng");
		entity = orderService.save(entity);

		
		for (Item item : cartItems) {
			item.setOrder(entity);
			item = itemService.save(item);
		}
		shoppingCartService.clearAll();
		return "redirect:/site/users";
	}

}
