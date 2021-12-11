package com.phamquyen.furniticaShop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.phamquyen.furniticaShop.controller.authen.LogginSuccessHandler;
import com.phamquyen.furniticaShop.service.impl.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
		return daoAuthenticationProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/admin/**")
			.hasAnyAuthority("ADMIN", "PRODUCT_MANAGER", "SALE_MANAGER",
					"USER_MANAGER", "ORDER_MANAGER")
			.antMatchers("/site/feedbacks/**", "/site/shoppingCart/checkout")
			.hasAnyAuthority("CUSTOMER")
			.anyRequest().permitAll()
			.and()
			.formLogin()
				.loginPage("/login")     
				.usernameParameter("email") 
				.passwordParameter("password")	
				.successHandler(successHandler)
				.permitAll() 
			.and()
			.logout() 
				.permitAll()     
			.and()
			.exceptionHandling().accessDeniedPage("/403")
			.and()
			.csrf().disable();
	}
 
	@Override  
	public void configure(WebSecurity web) throws Exception {
		// TODO Auto-generated method stub
		web.ignoring()
        	.antMatchers("/static/site/libs/**")
        	;
	}
	@Autowired  
	private LogginSuccessHandler successHandler;
	
}
