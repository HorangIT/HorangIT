package com.a101.ssafy.project.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item")
public class ItemController {
	@PostMapping
	public Object registerItem() {
		return null;
	}
	
}
