package com.a101.ssafy.project.controller;

import java.io.IOException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.a101.ssafy.project.model.BasicResponse;
import com.a101.ssafy.project.model.item.Item;
import com.a101.ssafy.project.model.item.RegisterDto;
import com.a101.ssafy.project.service.ItemService;
import com.a101.ssafy.project.service.S3Service;


@CrossOrigin(origins = { "*" })
@Controller
@RequestMapping("/post")
public class ItemController {
	ItemService itemService;
	
	@Autowired
	S3Service s3Service;
	
	@Autowired
	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	} 
	
	@PostMapping
	public Object registerItem(RegisterDto request, @RequestParam("files") MultipartFile[] multipartFiles) throws IOException {
		for(int i=0; i<multipartFiles.length; ++i) {
			s3Service.upload(multipartFiles[i]);
		}
		
		ResponseEntity response = null;
		
		Item item = new Item();
		BeanUtils.copyProperties(request, item);
		
		//위로 안 되는 것들 따로 설정(typeCasting->회의할것)
		item.setName(request.getTitle());
//		item.setStartPrice(Integer.parseInt(request.getStartPrice()));
//		item.setHappyPrice(Integer.parseInt(request.getHappyPrice()));
//		item.setGrade(Integer.parseInt(request.getGrade()));
//		item.setDirect(Integer.parseInt(request.getDirect()));
//		item.setUserId(Integer.parseInt(request.getUserId()));
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
