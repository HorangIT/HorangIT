package com.a101.ssafy.project.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.a101.ssafy.project.model.BasicResponse;
import com.a101.ssafy.project.model.image.Image;
import com.a101.ssafy.project.model.item.Item;
import com.a101.ssafy.project.model.item.RegisterDto;
import com.a101.ssafy.project.redis.RedisUtil;
import com.a101.ssafy.project.repository.ItemRepository;

@Service
public class ItemServiceImpl implements ItemService{
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm"); //날짜 맞출 포맷
	final String ITEM_NAME = "item";
	final String ITEM_EXPIRED = "Expired";
	
	@Autowired
	ItemRepository itemRepository;
	
	@Autowired
	RedisUtil redisUtil;
	
	@Autowired
	S3Service s3Service;
	
	@Override
	public BasicResponse registerItem(RegisterDto request, MultipartFile[] multipartFiles) throws ParseException {
		BasicResponse result = new BasicResponse();
		
		Item item = new Item();
		
		//Legacy -> 나중에 library 이용해보기 :: ModelMapper 라이브러리
		BeanUtils.copyProperties(request, item);
		
		item.setName(request.getTitle());
		item.setStartPrice(Integer.parseInt(request.getStartPrice()));
		item.setHappyPrice(Integer.parseInt(request.getHappyPrice()));
		item.setStatus(0); //경매 이전-혹은 경매 중이 default
		item.setStartDate(format.parse(request.getStartDateTime()));
		item.setEndDate(format.parse(request.getEndDateTime()));
		
		item.setDirect(Integer.parseInt(request.getDirect()));
		item.setGrade(request.getGrade().charAt(0));
		
		Date date = java.util.Calendar.getInstance().getTime();
		
		item.setCreatedAt(date);
		item.setUpdatedAt(date);
		
		System.out.println(item.toString()+"최종, 이미지 넣기 전");
		
		if(multipartFiles!=null) {
			for(int i=0; i<multipartFiles.length; ++i) {
				String url;
				try {
					url = s3Service.upload(multipartFiles[i]);
				} catch (IOException e) {
					result.status = false;
					result.data = "데이터 저장에 실패했습니다.";
					result.object = null;
					System.out.println("등록 실패");
					
					return result;
				}
				Image image = new Image(item, url);
			
				item.addImage(image);
			}
		}
		
		item = itemRepository.save(item);
		System.out.println("등록 성공");
		result.status = true;
		result.data = "데이터 저장에 성공했습니다.";
		result.object = null;
		
		long startTimeToEpochTime = item.getStartDate().getTime();
		long endTimeToEpochTime = item.getEndDate().getTime(); 
		
		long remainingTime = (endTimeToEpochTime - startTimeToEpochTime)/1000;
		redisUtil.setData(ITEM_NAME+item.getId(), "-1"); //expired trigger 오면 삭제해주기!
		redisUtil.setDataExpire(ITEM_NAME+item.getId()+ITEM_EXPIRED, endTimeToEpochTime+"", remainingTime); 
		
		return result;
	}

	@Override
	public JSONObject getItemById(long id){
		JSONObject jobj = null;
		
		Optional<Item> optional = itemRepository.findById(id);
		if(optional.isPresent()) {
			Item item = optional.get();
			
			System.out.println(ITEM_NAME+item.getId());
			jobj = new JSONObject();
			
			jobj.put("category", item.getCategory()); //카테고리
			jobj.put("description", item.getDescription()); //
			jobj.put("location", item.getLocation());
			jobj.put("name", item.getName());
			jobj.put("direct", item.getDirect());
			jobj.put("grade", item.getGrade());
			jobj.put("happyPrice", item.getHappyPrice());
			jobj.put("startPrice", item.getStartPrice());
			
			jobj.put("startDate", format.format(item.getStartDate()));
			jobj.put("endDate", format.format(item.getEndDate()));
			
			jobj.put("createdAt", format.format(item.getCreatedAt()));
			jobj.put("updatedAt", format.format(item.getUpdatedAt()));
			switch(item.getDirect()) {
			case 0:
				jobj.put("direct", "택배거래만 가능해요.");
				break;
			case 1:
				jobj.put("direct", "직거래만 가능해요.");
				break;
			case 2:
				jobj.put("direct", "택배와 직거래 둘 다 가능해요.");
				break;
			}
			
			Collection<Image> img = item.getImage();
			if(img.size()!=0) {
				Iterator<Image> iter = img.iterator();
				
				List<String> list = new ArrayList<>();
				while(iter.hasNext()) {
					String s = iter.next().getFilePath();
					list.add(s);
				}
				
				jobj.put("filePath", list);
			}
		}
		
		return jobj;
	}

}
