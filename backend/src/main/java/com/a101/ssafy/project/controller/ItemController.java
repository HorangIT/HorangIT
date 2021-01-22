package com.a101.ssafy.project.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.a101.ssafy.project.model.item.RegisterRequest;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/item")
public class ItemController {
	@PostMapping
	public Object registerItem(@RequestBody RegisterRequest request) {
		return null;
	}
	
}
