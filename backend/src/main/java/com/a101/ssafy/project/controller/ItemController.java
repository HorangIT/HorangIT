package com.a101.ssafy.project.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
import com.a101.ssafy.project.model.image.Image;
import com.a101.ssafy.project.model.item.Item;
import com.a101.ssafy.project.model.item.RegisterDto;
import com.a101.ssafy.project.model.search.SearchDto;
import com.a101.ssafy.project.model.search.SearchLocationDto;
import com.a101.ssafy.project.model.search.SearchSpecs;
import com.a101.ssafy.project.repository.ItemRepository;
import com.a101.ssafy.project.service.ItemService;
import com.a101.ssafy.project.service.S3Service;
import com.google.gson.JsonObject;

@CrossOrigin(origins = { "*" })
@Controller
@RequestMapping("/item")
public class ItemController {
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm"); //날짜 맞출 포맷

	@Autowired
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
	
	// pagination + filter!
	
	@GetMapping("/page/{number}/")
	public Object getAllItems(@PathVariable(name="number")int page) {

		try {
			// paging은 1~N까지 하기 때문에 기본 page에서 1을 빼준다 (0보다 큰 값이 들어올거니까)
			Pageable paging = PageRequest.of(page-1, 12);
			Page<Item> itemPage = itemRepository.findAll(paging);		
			List<Item> items = itemPage.getContent();	
			
			List<JSONObject> returningItems = itemService.getAllPages(items);
												
			BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "모든 아이템 보내기!";
			result.object = returningItems;
						
			return new ResponseEntity<>(result, HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/search/{search}")
	public Object searchWithFilter(@PathVariable String search) {
		
		Specification<Item> specify = Specification.where(SearchSpecs.location(search));
		Pageable paging = PageRequest.of(0, 5);
		Page<Item> p = itemRepository.findAll(specify, paging);
		List<Item> test = p.getContent();
		
		System.out.println(test.size());
				
		return null;
	}
	
	/*
	 * 지역 리스트 뽑아주기:
	 * - null이 들어오면 "시" 리턴
	 * - null이 아니면 "시-군구" 리턴
	 */
	@GetMapping("/district")
	@ResponseBody
	public Object getLocationNames(SearchLocationDto searchLocationDto) {
		
		try {
						
			JSONObject returnValue = new JSONObject();
			BasicResponse result = new BasicResponse();
			result.status = true;
			
			if(searchLocationDto.getDistrictName()==null) {
				List<String> districts = itemService.getDistrict();
				returnValue.put("districts", districts);	
				result.data = "시!!!간다!!!";
				
			} else {
				List<String> gunGu = itemService.getSiGunGu(searchLocationDto.getDistrictName());
				returnValue.put("gungu", gunGu);	
				result.data = "군구!!!간다!!!";
			}
			
			result.object = returnValue;
			
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		
	}
	
	
}
