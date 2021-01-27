package com.a101.ssafy.project.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.a101.ssafy.project.model.review.ReviewDto;
import com.a101.ssafy.project.repository.ItemRepository;
import com.a101.ssafy.project.service.ReviewService;


@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/review")
public class ReviewController {
	
	@Autowired
	ReviewService reviewService;
	
	
	@PostMapping
	public Object createReview(@RequestBody ReviewDto reviewRequest) {
				
		System.out.println("\n*******\nreviewRequest: "+reviewRequest.toString());
		
		return reviewService.createReview(reviewRequest);
	}
	
}
