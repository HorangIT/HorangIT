package com.a101.ssafy.project.model.image;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.a101.ssafy.project.model.item.Item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 송은주(OctopusSwellfish)
 * 
 * Image를 저장하기 위한 엔티티입니다.
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
public class Image {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(columnDefinition = "TEXT")
	private String filePath;
	
	//item 클래스를 가진 멤버 변수 이름이 item, Item Entity에서 봤던 mappedBy에서 "item" 뜻
	//@ManyToOne 어노테이션 기본 fetch 전략: EAGER
	@ManyToOne(optional = false)
	@JoinColumn(name="item_id")
	private Item item;
	
	public Image(Item item, String filePath) {
		this.item = item;
		this.filePath = filePath;
	}
}
