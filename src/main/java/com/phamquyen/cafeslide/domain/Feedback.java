package com.phamquyen.cafeslide.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "feedbacks")
public class Feedback implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "feedback_id")
	private Long feedbackId;
	
	@Column(name = "feedback_content", columnDefinition = "nvarchar(500) not null")
	private String feedbackContent;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "feedback_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date feedbackDate;
	
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;

}
