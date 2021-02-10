package com.a101.ssafy.project.model.search;

import java.beans.Expression;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
				List<Predicate> categoryList = new ArrayList<Predicate>();
				List<Predicate> gradeList = new ArrayList<Predicate>();
				
				Predicate allPred = null;
				Predicate siguPred = null;
				Predicate categoryPred = null;
				Predicate gradePred = null;
				
				// and 쓸지 or 쓸지를 위한 변수
				int cnt = 0;
				// boolean or = false;
				
				if (si != null && gu != null) {
					String arrAddress = (String)si + " " + (String)gu;
					
					siguPred = criteriaBuilder.like(root.get("location"), arrAddress+"%");
					cnt += 1;
				}
				if (categories != null) {
					String tmpCategory = (String)categories;
					String[] arrCategory = tmpCategory.split(",");
					
					for (int i = 0; i < arrCategory.length; i++) {
						System.out.println(arrCategory[i]);
						categoryList.add(criteriaBuilder.equal(root.get("category"), arrCategory[i]));
					}						
					categoryPred = criteriaBuilder.or(categoryList.toArray(new Predicate[categoryList.size()]));
					cnt += 1;
				}
				if (grades != null) {
					String tmpGrades = (String)grades;
					char[] arrGrades = tmpGrades.toCharArray();
					// input 데이터의 갯수만큼 grade에 일치하는 데이터가 있다면 list에 추가해준다
					for (int i = 0; i < arrGrades.length; i++) {
						if (arrGrades[i] != ',') {
							System.out.println(arrGrades[i]);
							gradeList.add(criteriaBuilder.equal(root.get("grade"), arrGrades[i]));							
						}
					}
					gradePred = criteriaBuilder.or(gradeList.toArray(new Predicate[gradeList.size()]));
					cnt += 1;
				}
				
				if (siguPred != null)
					allPred = siguPred;
				
				if (categoryPred != null) {
					if (allPred == null) allPred = categoryPred;
					else allPred = criteriaBuilder.and(allPred, categoryPred);
				}
				
				if (gradePred != null) {
					if (allPred == null) allPred = gradePred;
					else allPred = criteriaBuilder.and(allPred, gradePred);
				}

				return allPred;
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
