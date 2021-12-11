package com.phamquyen.furniticaShop.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.ManyToAny;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long productId;
	
	@Column(name = "product_name", columnDefinition = "nvarchar(50) not null", unique = true)
	private String productName;
	
	@Column(name = "quantity", nullable = false)
	private int quantity;
	@Column(name = "description", columnDefinition = "nvarchar(1000) not null")
	private String description;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "entered_date")
	private Date enteredDate;
	
	@Column(name = "unit_price", nullable = false)
	private int unitPrice;  
	
	@Column(name = "image_url", columnDefinition = "nvarchar(100)")
	private String imageUrl;
	
	@Column(name = "size", columnDefinition = "nvarchar(20) not null")
	private String size;
	 
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "categoryId")
	private Category category;
	
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	private Set<Discount> discounts; 
	
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	private List<Item> items; 
	
	@Transient
	public String getImageUrl() {
		if( imageUrl == null) return null;
		
		return "/upload/" + imageUrl;  
	}

}
