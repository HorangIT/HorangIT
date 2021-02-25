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
import java.util.stream.Stream;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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

import com.a101.ssafy.project.model.BasicResponse;
import com.a101.ssafy.project.model.PaginationResponse;
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

/**
 * @author 송은주(OctopusSwellfish)
 * 경매 물품 등록 및 조회에 대응하기 위한 클래스입니다.
 * 
 * 경매 물품을 등록하고 상세정보를 확인할 수 있는 함수가 있는 클래스입니다. 
 * 
 */
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
	
	/** 
	 * 해당하는 아이템 id를 요청하면 해당 아이템의 상세 정보를 리턴하는 함수입니다. 
	 * */
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
			
			response = new ResponseEntity<>(result, HttpStatus.OK);
		}
		
		return response;
	}
	
	/** 
	 * 아이템 정보를 받으면 이를 등록하는 함수입니다. 
	 * */
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
	
	/** 
	 * @author 이지영
	 * */
	// pagination + filter!	
	@GetMapping("/page/{pageNo}")
	public Object searchItems(@PathVariable int pageNo, SearchDto searchDto) {
		
//		BasicResponse result = new BasicResponse();
		PaginationResponse result = new PaginationResponse();
		List<JSONObject> returningItems = new ArrayList<JSONObject>();
		Pageable paging = PageRequest.of(pageNo-1, 12, Sort.by("startDate").descending());
		Page<Item> pages;
		boolean empty = false;
		
		// DTO에 아무것도 안들어왔을 때 = 그냥 전체 페이지 요청
		if (searchDto.getCategory() == null && searchDto.getGrade() == null 
				&& searchDto.getSi() == null && searchDto.getGu() == null
				&& searchDto.getName() == null && searchDto.isStatus() == false) {
			System.out.println("EMPTYYYYYYYYYY");
			pages = itemRepository.findAll(paging);
			empty = true;
		} 
		// 특정 이름/분야별로 찾을 때
		else {
			Specification<Item> specify = Specification
					.where(SearchSpecs.searchWithFilter(searchDto.getSi(), searchDto.getGu(), searchDto.getGrade(), searchDto.getCategory(), searchDto.getName(), searchDto.isStatus()));
			pages = itemRepository.findAll(specify, paging);
			
		}		
		List<Item> contents = pages.getContent();
		returningItems = itemService.getAllPages(contents);
		
		if (empty) {
			result.status = true;
			result.data = "모든 아이템 보내기!";
			result.object = returningItems;
			result.page = pages.getNumber()+1;			// 현재 페이지 번호
			result.total_elements = pages.getTotalElements(); 	// 전체 요소 갯수
			result.total_pages = pages.getTotalPages(); // 전체 페이지 수
						
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		else if (returningItems.size() == 0) {
			result.status = false;
			result.object = null;
			result.data = "못 찾았어요ㅠ";
			
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			result.data = "이거시 네가 찾던 자료냥";
			result.status = true;
			result.object = returningItems;
			result.page = pages.getNumber()+1;			// 현재 페이지 번호
			result.total_elements = pages.getTotalElements(); 	// 전체 요소 갯수
			result.total_pages = pages.getTotalPages(); // 전체 페이지 수
			
			return new ResponseEntity<>(result, HttpStatus.OK);			
		}
		
	}
		
	/**
	 * @author 이지영
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

	/**
	 * 아이템이 응찰 되었을 때, 해당 아이템의 거래를 위해 판매자-구매자간 1:1채팅을 열게 되는데, 이 때의 채팅 로그를 가져오는 함수입니다.
	 */
	@GetMapping("/{itemId}/chat")
	@ResponseBody
	public Object getChatLog(@PathVariable("itemId")long itemId) {
		BasicResponse result = itemService.getChatLog(itemId);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	/**
	 * 해당 아이템의 chatting 이전 로그를 한 번에 리턴하는 함수입니다.
	 */
	@GetMapping("/{itemId}/chatroom")
	@ResponseBody
	public Object getChatRoomLog(@PathVariable("itemId")long itemId) {
		BasicResponse result = itemService.getChatRoomLog(itemId);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
}
