package com.a101.ssafy.project.model.search;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 송은주(OctopusSwellfish)
 * 
 * District를 저장하기 위한 엔티티입니다. (시)
 * district에 여러 si-gun-gu가 들어갈 수 있기 때문에, district:si-gun-gu=1:N으로 맵핑한 구조입니다.
 * 
 * 맵핑 시 조인 컬럼은 district_id입니다.
 *
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class SiGunGu {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	String name;
	
	@ManyToOne(optional = false)
	@JoinColumn(name="district_id")
	private District district;
	
	
}
