package com.a101.ssafy.project.model.search;

import java.beans.Expression;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.a101.ssafy.project.model.item.Item;

import io.swagger.models.Path;

public class SearchSpecs {
		
	public static Specification<Item> searchWithFilter(Object si, Object gu, Object grades, Object categories) {
		return new Specification<Item>() {

			@Override
			public Predicate toPredicate(Root<Item> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				
				// 여러 데이터가 있을 수 있으니 List로 만든다
				List<Predicate> predicates = new ArrayList<Predicate>();
				
				// and 쓸지 or 쓸지를 위한 변수
				int cnt = 0;
				
				if (si != null && gu != null) {
					String arrAddress = (String)si + " " + (String)gu;
					
					predicates.add(criteriaBuilder.like(root.get("location"), arrAddress+"%"));
					cnt += 1;
				}
				if (grades != null) {
					char[] arrGrades = (char[])grades;
					// input 데이터의 갯수만큼 grade에 일치하는 데이터가 있다면 list에 추가해준다
					for (int i = 0; i < arrGrades.length; i++) {
						System.out.println(arrGrades[i]);
						predicates.add(criteriaBuilder.equal(root.get("grade"), arrGrades[i]));
					}
					cnt += 1;
				}
				if (categories != null) {
					String[] arrCategory = (String[])categories;
					for (int i = 0; i < arrCategory.length; i++) {
						System.out.println(arrCategory[i]);
						predicates.add(criteriaBuilder.equal(root.get("category"), arrCategory[i]));
					}
					cnt += 1;
				}
				
				// 동시에 여러 파라미터 == and
				if (cnt > 1) {
					return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
				}
				// 공통 카테고리안에서 == or
				return criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
				
			}
		};
	}
	

	public static Specification<Item> price(final long minPrice, final long maxPrice){
		return new Specification<Item>() {
			@Override
			public Predicate toPredicate(Root<Item> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.between(root.get("startPrice"), minPrice, maxPrice);
			}
		
		};
	
	}
		
}
