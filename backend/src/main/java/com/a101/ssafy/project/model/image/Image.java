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

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Image {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
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
