package com.a101.ssafy.project.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.a101.ssafy.project.model.search.SearchDto;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/search")
public class SearchController {
	
	/** 검색할 때 */
	@GetMapping
	public Object searchWithFilter(@RequestBody SearchDto searchDto) {
//		if()
		return null;
	}
	

}
