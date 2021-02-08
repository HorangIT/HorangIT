package com.a101.ssafy.project.model.search;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.a101.ssafy.project.model.item.Item;

public class SearchSpecs {
	public static Specification<Item> price(final long minPrice, final long maxPrice){
		return new Specification<Item>() {
			@Override
			public Predicate toPredicate(Root<Item> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.between(root.get("startPrice"), minPrice, maxPrice);
			}
		
		};
	
	}
	
}
