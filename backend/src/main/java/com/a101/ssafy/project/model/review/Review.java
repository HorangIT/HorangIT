package com.a101.ssafy.project.model.review;

import java.util.Date;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.a101.ssafy.project.model.receipt.Receipt;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Review {
	
	// review id = receipt id
	@Id
	@Column
	private Long id;

	@Column(length = 250)
	private String content;

	@Column
	private int starRating;

	@Temporal(TemporalType.DATE)
	@Column
	private Date createdAt;

	@OneToOne
	@MapsId		//mapsId를 통해 receipt의 id가 review의 id로 자동 매핑된다 (이름도 알아서 receipt_id로 생성됨)
	@JoinColumn
	private Receipt receipt;

}
