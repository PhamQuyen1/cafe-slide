package com.phamquyen.cafeslide.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyConfiger implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		Path uploadDir = Paths.get("./upload"); 
		String uploadPath = uploadDir.toFile().getAbsolutePath();
		
		registry.addResourceHandler("/upload/**").addResourceLocations("file:/" + uploadPath + "/");
		registry.addResourceHandler("/resources/static");
	}

	@Override  
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/403").setViewName("/admin/403"); 
		
	}
 
}
