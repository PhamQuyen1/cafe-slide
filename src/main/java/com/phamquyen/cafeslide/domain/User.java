package com.phamquyen.cafeslide.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ManyToAny;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "email", columnDefinition = "nvarchar(100) not null")
	private String email;
	
	@Column(name = "password", columnDefinition = "nvarchar(200) not null")
	private String password;
	
	@Column(name = "fullname", columnDefinition = "nvarchar(100) not null")
	private String fullname;
	
	@Column(name = "phone", columnDefinition = "nvarchar(12) not null")
	private String phone;
	
	@Column(name = "address", columnDefinition = "nvarchar(100) not null")
	private String address;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "birthday")
	@DateTimeFormat(pattern = "yyyy-MM-dd")  
	private Date birthday;
	
	@Column(name = "status", columnDefinition = "not null")
	private int status;
	
	@Column(name = "active")
	private boolean active;
	
	@ManyToOne
	@JoinColumn(name = "roleId")
	private Role role;  
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Feedback> feedbacks;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Order> orders; 
	
	public boolean hasRole(String roleName) {
		return this.role.getRoleName().equals(roleName); 
	}  
	
}
