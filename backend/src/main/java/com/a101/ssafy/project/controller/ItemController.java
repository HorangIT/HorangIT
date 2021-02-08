package com.a101.ssafy.project.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.a101.ssafy.project.model.BasicResponse;
import com.a101.ssafy.project.model.item.Item;
import com.a101.ssafy.project.model.item.RegisterDto;
import com.a101.ssafy.project.repository.ItemRepository;
import com.a101.ssafy.project.service.ItemService;
import com.a101.ssafy.project.service.S3Service;

@CrossOrigin(origins = { "*" })
@Controller
@RequestMapping("/item")
public class ItemController {
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm"); //날짜 맞출 포맷

	ItemService itemService;

	@Autowired
	S3Service s3Service;
	
	@Autowired
	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	} 
	
	// pagination을 위한 itemRepo
	@Autowired
	ItemRepository itemRepository;
	
	//이후 주석 꼭 달기!
	
	@GetMapping("/{id}")
	@ResponseBody
	public Object readItem(@PathVariable("id")long id) {
		JSONObject jobj = itemService.getItemById(id);
		
		ResponseEntity response = null;
		final BasicResponse result = new BasicResponse();
		
		if(jobj!=null) {
			result.status = true;
			result.data = "조회에 성공했습니다.";
			result.object = jobj;			
			
			response = new ResponseEntity<>(result, HttpStatus.OK);
		}else {
			result.status = false;
			result.data = "조회할 데이터가 없습니다.";
			result.object = null;
			
			response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}
		
		return response;
	}
	
	
	@PostMapping
	public Object registerItem(RegisterDto request, @RequestParam("files") MultipartFile[] multipartFiles) throws IOException, ParseException {
		BasicResponse result = itemService.registerItem(request, multipartFiles);
		ResponseEntity response = null;
		if(result.status==true) {
			response = new ResponseEntity<>(result, HttpStatus.OK);
		}
		else {
			response = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}

		return response;
	}
	
	
	@GetMapping("/page")
	public Object getAllItems(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue ="12") int size) {

		try {
			
			PageRequest paging = PageRequest.of(page, size);
			Page<Item> itemPage = itemRepository.findAll(paging);
			
			System.out.println(itemPage.toString());
//			
			List<Item> items = itemPage.getContent();
			System.out.println(items.size());
			System.out.println(items.get(0).getName());
			
			
//			Page<Item> itemPage = itemRepository.findByCategory("전자기기", paging);
//			
//			System.out.println(items.toString());
//						
//			
//			Map<String, Object> response = new HashMap<String, Object>();
//			response.put("items", items);
//			response.put("currentPage", itemPage.getNumber());
//			response.put("totalPages", itemPage.getTotalPages());
//			
//			System.out.println(response.toString());
			
			return new ResponseEntity<>(itemPage, HttpStatus.OK);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}


	}
	
}
