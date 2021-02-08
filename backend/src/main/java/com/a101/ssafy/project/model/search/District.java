package com.a101.ssafy.project.model.search;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class District {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String name;
	
	private Collection<SiGunGu> siGunGu = new ArrayList<>();
	
	private void addsiGunGu(final SiGunGu sgg) {
		siGunGu.add(sgg);
	}
	
	private void removeImage(final SiGunGu sgg) {
		siGunGu.remove(sgg);
		sgg.setDistrict(null);
	}

}
