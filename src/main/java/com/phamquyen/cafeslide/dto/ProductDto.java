package com.phamquyen.cafeslide.dto;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto implements Serializable{
	
	private Long productId;
	private String productName;
	private String description;
	private int quantity;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date enteredDate;
	private String size;    
	private String imageUrl;
	private MultipartFile productImage; 
	private int unitPrice; 
	private String categoryName;  
	private int price;  
}
