package com.a101.ssafy.project.controller;

import java.io.IOException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.a101.ssafy.project.image.Image;
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
	
	@GetMapping("/{id}")
	@ResponseBody
	public Object readItem(@PathVariable("id")long id) {
		
		return "HI";
	}
	
	@PostMapping
	public Object registerItem(RegisterDto request, @RequestParam("files") MultipartFile[] multipartFiles) throws IOException {
		ResponseEntity response = null;
		
		Item item = new Item();
		BeanUtils.copyProperties(request, item);
		System.out.println(request.toString()+"리퀘스트");
		System.out.println(item.toString()+"아이템");
		//위로 안 되는 것들 따로 설정(typeCasting->회의할것)
		item.setName(request.getTitle());
		item.setCategory("전자기기");
		item.setStartPrice(Integer.parseInt(request.getStartPrice()));
		item.setHappyPrice(Integer.parseInt(request.getHappyPrice()));
//		item.setGrade(Integer.parseInt(request.getGrade()));
//		item.setDirect(Integer.parseInt(request.getDirect()));
		item.setDirect(1);
		item.setGrade(2);
//		item.setUserId(Integer.parseInt(request.getUserId()));
		//이후 date 설정 해야함!
		System.out.println(item.toString()+"다 만든이후");

		final BasicResponse result = new BasicResponse();
		result.status = true;
		result.data = "테스트";
		result.object = null;
		response = new ResponseEntity<>(result, HttpStatus.OK);
		
		if(multipartFiles!=null) {
			for(int i=0; i<multipartFiles.length; ++i) {
				String url = s3Service.upload(multipartFiles[i]);
				Image image = new Image(item, url);
				
				item.addImage(image);
			}			
		}
//		System.out.println(item.toString()+"냐옹");
		if(itemService.registerItem(item)) {
//			System.out.println(item.getId()+"ggg");
			System.out.println("등록 성공");
		}else {
			System.out.println("등록 실패");
		}
		return response;
	}
	
}
