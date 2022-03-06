package com.phamquyen.cafeslide.dto;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CategoryDto implements Serializable{
	
	private Long categoryId;
	
	@NotEmpty
	@Min(value = 5)
	private String categoryName;
	
//	private int totalProduct;
}
