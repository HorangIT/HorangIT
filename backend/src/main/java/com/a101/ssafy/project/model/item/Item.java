package com.a101.ssafy.project.model.item;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.a101.ssafy.project.image.Image;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column
	//나중에 수정 (buyer id)
	private long userId;
	
	private String name;
	private String category;
	private String location;
	private String description;
	
	private int startPrice;
	private int happyPrice;
	private int grade;
	private int direct;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;
	private Date endDate;
	private Date createdAt;
	private Date updatedAt;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "item") 
	public Collection<Image> image;
}
