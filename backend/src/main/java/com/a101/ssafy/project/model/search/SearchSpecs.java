package com.a101.ssafy.project.model.search;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import com.a101.ssafy.project.model.item.Item;

public class SearchSpecs {
		
	public static Specification<Item> searchWithFilter(Object si, Object gu, Object grades, Object categories, Object name) {
		return new Specification<Item>() {

			@Override
			public Predicate toPredicate(Root<Item> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				
				// 중복 검색이 가능한 분야들은 리스트로 만들어서 담을 예정
				List<Predicate> categoryList = new ArrayList<Predicate>();
				List<Predicate> gradeList = new ArrayList<Predicate>();
				
				// 최종 검색결과 (allPredicates)에 최종적으로 생성될 각 분야의 predicate들을 더해줄 예정
				Predicate allPredicates = null;
				Predicate siguPred = null;
				Predicate categoryPred = null;
				Predicate gradePred = null;
				Predicate namePred = null;
				
				// 이름으로 찾기
				if (name != null) {
					String itemName = (String)name;
					namePred = criteriaBuilder.like(root.get("name"), "%"+itemName+"%");
				}

				// 찾고자 하는 주소지가 존재한다면 주소Predicate에 검색결과를 넣어줌
				if (si != null && gu != null) {
					String arrAddress = (String)si + " " + (String)gu;
					siguPred = criteriaBuilder.like(root.get("location"), arrAddress+"%");

				}
				// 카테고리는 "A,B"형식으로 들어오기 때문에 ","를 기준으로 배열을 만들어 준 뒤에 배열 내의 분야들의 검색결과를 categoryList에 넣는다
				// 그 중 겹치는 결과들만 최종 categoryPred에 넣어준다
				if (categories != null) {
					String tmpCategory = (String)categories;
					String[] arrCategory = tmpCategory.split(",");
					
					for (int i = 0; i < arrCategory.length; i++) {
						System.out.println(arrCategory[i]);
						categoryList.add(criteriaBuilder.equal(root.get("category"), arrCategory[i]));
					}						
					categoryPred = criteriaBuilder.or(categoryList.toArray(new Predicate[categoryList.size()]));
				}
				// 등급은 카테고리와 같은 방식으로 이뤄짐
				if (grades != null) {
					String tmpGrades = (String)grades;
					char[] arrGrades = tmpGrades.toCharArray();
					
					for (int i = 0; i < arrGrades.length; i++) {
						if (arrGrades[i] != ',') {
							System.out.println(arrGrades[i]);
							gradeList.add(criteriaBuilder.equal(root.get("grade"), arrGrades[i]));							
						}
					}
					gradePred = criteriaBuilder.or(gradeList.toArray(new Predicate[gradeList.size()]));
				}
				
				// 각 검색결과에 대해서 allPredicates에 넣어준다
				// 만약 allPredicates가 비어있지 않다면 검색결과들을 합친 후 다시 allPredicates에 넣어준다11
				if (namePred != null) allPredicates = namePred;
				
				if (siguPred != null) {
					if (allPredicates == null) allPredicates = categoryPred;
					else allPredicates = criteriaBuilder.and(allPredicates, siguPred);
				}
				
				if (categoryPred != null) {
					if (allPredicates == null) allPredicates = categoryPred;
					else allPredicates = criteriaBuilder.and(allPredicates, categoryPred);
				}
				
				if (gradePred != null) {
					if (allPredicates == null) allPredicates = gradePred;
					else allPredicates = criteriaBuilder.and(allPredicates, gradePred);
				}

				return allPredicates;
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
