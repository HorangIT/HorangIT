package com.a101.ssafy.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.a101.ssafy.project.model.item.Item;
import com.a101.ssafy.project.model.search.SearchDto;
import com.a101.ssafy.project.model.search.SearchSpecs;
import com.a101.ssafy.project.repository.SearchRepository;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/search")
public class SearchController {
	
	@Autowired
	SearchRepository searchRepository;
	
	/** 검색할 때 */
	@GetMapping
	public Object searchWithFilter(SearchDto searchDto) {
		Specification<Item> spe = Specification.where(SearchSpecs.price(10000, 20000));
		PageRequest pageRequest = PageRequest.of(0, 5);
		Page<Item> p = searchRepository.findAll(spe, pageRequest);
		
		List<Item>p1 = searchRepository.findAll(spe);
		
//		System.out.println(p.toString());
//		for(int i=0; i<p.size(); ++i) {
//			System.out.println(p.get(i).getId());
//		}
		
		System.out.println(p1.size()+"전체 사이증ㅎㅎ");
		System.out.println(p.getSize() + "page의 사이즈");
		System.out.println(p.getNumber() + "page의 넘버");
		
		List<Item> kk = p.getContent();
		for(int i=0; i<kk.size(); ++i) {
			System.out.println(kk.get(i).getId()+"zz");
		}
		return "hi";
	}
	

}
