package com.phamquyen.cafeslide.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "role_id")
	private Long roleId;
	@Column(name = "role_name", columnDefinition = "nvarchar(50) not null")
	private String roleName;
	@Column(name = "description", columnDefinition = "nvarchar(200) not null")
	private String description;
	
	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
	private Set<User> users;
}
