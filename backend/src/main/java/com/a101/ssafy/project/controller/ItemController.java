package com.a101.ssafy.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.a101.ssafy.project.model.BasicResponse;
import com.a101.ssafy.project.model.item.RegisterRequest;


@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/item")
public class ItemController {
	@PostMapping
	public Object registerItem(@RequestBody RegisterRequest request) {
		System.out.println(request.toString());
		
		ResponseEntity response = null;
		
		final BasicResponse result = new BasicResponse();
		result.status = true;
		result.data = "테스트";
		result.object = null;
		response = new ResponseEntity<>(result, HttpStatus.OK);
		
		return response;
	}
	
}
