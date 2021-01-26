package com.a101.ssafy.project.model.receipt;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import com.a101.ssafy.project.model.review.Review;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Receipt {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	// item id 타입 물어보기
	@Column
	private Long itemId;
	
	@Column
	private Long buyerId;
	
	@Column
	private Long sellerId;
	
	@Column
	private int finalPrice;
	
	@Column
	private int deliveryType;
	
	@OneToOne(mappedBy = "receipt", cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn	//review를 pk로 묶기 위한 annotation
	private Review review;
		
}
