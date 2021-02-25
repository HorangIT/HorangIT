package com.a101.ssafy.project.model.item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.a101.ssafy.project.model.image.Image;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * @author 송은주(OctopusSwellfish)
 * 
 * Items를 저장하기 위한 엔티티입니다. (경매 물품)
 * 아이템에 여러 이미지가 들어갈 수 있기 때문에, 아이템:이미지=1:N으로 맵핑한 구조입니다.
 * 
 * 맵핑 시 조인 컬럼은 itemId입니다.
 *
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	//나중에 수정 (buyer id)
	private Long userId;
	
	private String name;
	private String category;
	private String location;
	private String description;
	
	private long startPrice;
	private long happyPrice;
	private char grade;
	private int direct;
	private int status;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;
	private Date endDate;
	private Date createdAt;
	private Date updatedAt;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="item_id")
	public Collection<Image> image = new ArrayList<>();

	public void addImage(final Image img) {
		image.add(img);
	}
	
	public void removeImage(final Image img) {
		image.remove(img);
		img.setItem(null);
	}
	
}
