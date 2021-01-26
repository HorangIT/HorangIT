package com.a101.ssafy.project.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.a101.ssafy.project.model.BasicResponse;
import com.a101.ssafy.project.model.item.Item;
import com.a101.ssafy.project.model.item.RegisterDto;
import com.a101.ssafy.project.service.ItemService;
import com.a101.ssafy.project.service.S3Service;


@CrossOrigin(origins = { "*" })
@Controller
@RequestMapping("/item")
public class ItemController {
	ItemService itemService;
	
	@Autowired
	S3Service s3Service;
	
	@Autowired
	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}
	
	@PostMapping
	public Object registerItem(@RequestBody RegisterDto request) {
		System.out.println(request.toString());
		
		ResponseEntity response = null;
		
		Item item = new Item();
		
		BeanUtils.copyProperties(request, item);
		
		//String imgPath = s3Service.upload(file)
		//file <- MultipartFile file
		//galleryDto.setFilePath(imgPath);
		
		//galleryService.savePost(galleryDto);
		//위로 안 되는 것들 따로 설정(typeCasting->회의할것)
		item.setName(request.getTitle());
		item.setStartPrice(Integer.parseInt(request.getStartPrice()));
		item.setHappyPrice(Integer.parseInt(request.getHappyPrice()));
		item.setGrade(Integer.parseInt(request.getGrade()));
		item.setDirect(Integer.parseInt(request.getDirect()));
		item.setUserId(Integer.parseInt(request.getUserId()));
		//이후 date 설정 해야함!
		System.out.println(item.toString());
		
		itemService.registerItem(item);
		final BasicResponse result = new BasicResponse();
		result.status = true;
		result.data = "테스트";
		result.object = null;
		response = new ResponseEntity<>(result, HttpStatus.OK);
		
		return response;
	}
	
}
