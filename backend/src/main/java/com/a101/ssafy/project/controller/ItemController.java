package com.a101.ssafy.project.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.a101.ssafy.project.image.Image;
import com.a101.ssafy.project.model.BasicResponse;
import com.a101.ssafy.project.model.item.Item;
import com.a101.ssafy.project.model.item.RegisterDto;
import com.a101.ssafy.project.service.ItemService;
import com.a101.ssafy.project.service.S3Service;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


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
	
	//이후 주석 꼭 달기!
	
	@GetMapping("/{id}")
	@ResponseBody
	public Object readItem(@PathVariable("id")long id) {
		Item item = itemService.getItemById(id);

		JsonObject jobj = new JsonObject();
		jobj.addProperty("category", item.getCategory()); //카테고리
		jobj.addProperty("description", item.getDescription()); //
		jobj.addProperty("location", item.getLocation());
		jobj.addProperty("name", item.getName());
		jobj.addProperty("direct", item.getDirect());
		jobj.addProperty("grade", item.getGrade());
		jobj.addProperty("happyPrice", item.getHappyPrice());
		jobj.addProperty("startPrice", item.getStartPrice());
		
		jobj.addProperty("startDate", format.format(item.getStartDate()));
		jobj.addProperty("endDate", format.format(item.getEndDate()));
		
		jobj.addProperty("createdAt", format.format(item.getCreatedAt()));
		
		
		Collection<Image> hi = item.getImage();
		if(hi.size()!=0) {
			JsonArray jarr = new JsonArray();
			Iterator<Image> iter = hi.iterator();

			while(iter.hasNext()) {
				String s = iter.next().getFilePath();
				jarr.add(s);
			}
			jobj.add("filePath", jarr);
		}
		return jobj.toString();
	}
	
	@PostMapping
	public Object registerItem(RegisterDto request, @RequestParam("files") MultipartFile[] multipartFiles) throws IOException, ParseException {

		ResponseEntity response = null;
		
		Item item = new Item();
		BeanUtils.copyProperties(request, item);
		System.out.println(request.toString()+"리퀘스트");
		System.out.println(item.toString()+"아이템");
		//위로 안 되는 것들 따로 설정(typeCasting->회의할것)
		item.setName(request.getTitle());
		item.setCategory("전자기기"); //카테고리도 안 옴
		item.setStartPrice(Integer.parseInt(request.getStartPrice()));
		item.setHappyPrice(Integer.parseInt(request.getHappyPrice()));
		item.setStatus(0); //status 는 기본 0으로 설정
		item.setStartDate(format.parse(request.getStartDate()));
		item.setEndDate(format.parse(request.getEndDate()));
		
		Date date = java.util.Calendar.getInstance().getTime();
		
		item.setCreatedAt(date);
		item.setUpdatedAt(date);
//		item.setCreatedAt(format.);
		
		//direct에 대한 처리 필요
		item.setDirect(1);
		item.setGrade('S');
		/////////////
		
		
//		item.setGrade(Integer.parseInt(request.getGrade()));
//		item.setDirect(Integer.parseInt(request.getDirect()));
//		item.setGrade(request.getGrade().charAt(0));
//		item.setUserId(Integer.parseInt(request.getUserId()));
		//이후 date 설정 해야함!
		System.out.println(item.toString()+"다 만든이후");

		final BasicResponse result = new BasicResponse();
		
		if(multipartFiles!=null) {
			for(int i=0; i<multipartFiles.length; ++i) {
				String url = s3Service.upload(multipartFiles[i]);
				Image image = new Image(item, url);
				
				item.addImage(image);
			}			
		}
		if(itemService.registerItem(item)) {
			result.status = true;
			result.data = "물품 등록에 성공했습니다.";
			result.object = null;
			response = new ResponseEntity<>(result, HttpStatus.OK);
			System.out.println("등록 성공");
		}else {
			result.status = false;
			result.data = "물품 등록에 실패했습니다.";
			result.object = null;
			response = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);			
			System.out.println("등록 실패");
		}
		return response;
	}
	
}
