package com.phamquyen.cafeslide.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable{
	
	private Long userId;
	@Min(value = 5)
	@NotEmpty
	private String email;
//	@NotEmpty
	private String password; 
	@NotEmpty
	private String fullname; 
	@NotEmpty
	private String phone;
	private String address; 
	private boolean active;
	private int status;
	@NotEmpty 
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;
	private String roleName;   
}
