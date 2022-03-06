package com.phamquyen.cafeslide.controller.authen;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import com.phamquyen.cafeslide.dto.MyUserDetails;

@Component
public class LogginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		HttpSession session = request.getSession();
		MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
		Collection<? extends GrantedAuthority> authorities = myUserDetails.getAuthorities();
		String redirectUrl = request.getContextPath();
		SavedRequest savedRequest = (SavedRequest) session.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
		
		if(myUserDetails.hasRole("ADMIN") || myUserDetails.hasRole("PRODUCT_MANAGER") || 
				myUserDetails.hasRole("SALE_MANAGER") || myUserDetails.hasRole("USER_MANAGER") || 
				myUserDetails.hasRole("ORDER_MANAGER")) {
			redirectUrl += "/admin/home";
			System.out.println(redirectUrl);
		}else {
			if(savedRequest != null)
			redirectUrl = savedRequest.getRedirectUrl();
			else redirectUrl += "/site/home";
		}
		
		response.sendRedirect(redirectUrl);
	}

	

}
