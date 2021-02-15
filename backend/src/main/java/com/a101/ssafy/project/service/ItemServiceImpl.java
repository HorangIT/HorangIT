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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.a101.ssafy.project.model.BasicResponse;
import com.a101.ssafy.project.model.image.Image;
import com.a101.ssafy.project.model.item.Item;
import com.a101.ssafy.project.model.item.RegisterDto;
import com.a101.ssafy.project.model.search.District;
import com.a101.ssafy.project.model.search.SiGunGu;
import com.a101.ssafy.project.redis.RedisUtil;
import com.a101.ssafy.project.repository.DistrictRepository;
import com.a101.ssafy.project.repository.ItemRepository;

@Service
public class ItemServiceImpl implements ItemService{
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm"); //날짜 맞출 포맷
	final String ITEM_NAME = "item";
	final String ITEM_EXPIRED = "Expired";
	final String ITEM_HAPPY_PRICE = "Happy";
	final String ITEM_CHAT_LOG_USER_ID = "itemChatUserId";
	final String ITEM_CHAT_LOG_USER_CONTENT = "itemChatUserContent";
	final String ITEM_CHAT_LOG_USER_TIME = "itemChatUserTime";
	final String ITEM_CHAT_LOG_USER_NICKNAME = "itemChatNickname";
	
	@Autowired
	ItemRepository itemRepository;
	
	@Autowired
	RedisUtil redisUtil;
	
	@Autowired
	S3Service s3Service;
	
	@Autowired
	AuctionService auctionService;
	
	@Autowired
	DistrictRepository districtRepository;
	
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
		
		item.setUserId(Long.parseLong(request.getUid()));
		
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
		redisUtil.setData(ITEM_NAME+item.getId(), item.getStartPrice()+""); //expired trigger 오면 삭제해주기!
//		startPrice <- 사는거 (처음에는 삼 ㅋㅋ)
		//expired trigger 오면 삭제해주기!
		redisUtil.setData(ITEM_NAME+item.getId()+ITEM_HAPPY_PRICE, item.getHappyPrice()+"");
		redisUtil.setDataExpire(ITEM_NAME+item.getId()+ITEM_EXPIRED, endTimeToEpochTime+"", remainingTime); 
		
		return result;
	}

	@Override
	public JSONObject getItemById(long id){
		JSONObject jobj = null;
		Date now = new Date();
		Optional<Item> optional = itemRepository.findById(id);
		if(optional.isPresent()) {
			Item item = optional.get();
			
			if (item.getStartDate().after(now)) {
				System.out.println("시작하지 않은 경매");
				return null;
			}
			
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
			jobj.put("itemId", item.getId());
			
			String str = redisUtil.getData(ITEM_NAME+item.getId()); //사람이 살가격
			jobj.put("nowPrice", Long.parseLong(str));
			
			long nextPrice = auctionService.getAuctionUnit(str) + Long.parseLong(str); //다음 응찰가격은 nextPrice입니다.
			if(nextPrice >= item.getHappyPrice()) {
				nextPrice = item.getHappyPrice();
				jobj.put("test", "응찰 가격을 넘어섰어요! 이제 사야해요.");
			}
			jobj.put("nextPrice", nextPrice);
			
			
			jobj.put("nickname", redisUtil.getHdata("user", item.getUserId()+""));
			
			
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
	
	// SEARCH & FILTER METHODS
	@Override
	public List<JSONObject> getAllPages(List<Item> items) {
		/*
		 * Items의 image가 collections 형태이기 때문에 jpa가 알아서 serialization을 해주지 못함
		 * 따라서 직접 리턴해줄 item들의 리스트 형태를 지정해줬음
		 * 
		 * returningItems = [
		 * 	{
		 * 		"itemId": 1,
		 * 		"image": "filePath",
		 * 		"grade": "S",
		 * 		"name": "1",
		 * 		"category": "category",
		 * 		"startDate": "datetime"
		 * ] 
		 * 
		 */
		
		List<JSONObject> returningItems = new ArrayList<JSONObject>();
		
		Date now = new Date();		
		for (int i = 0; i < items.size(); i++) {
			JSONObject jobj = new JSONObject();
			
			// 시작하지 않은 경매를 제외한 경매들을 불러옴	
			if (items.get(i).getStartDate().after(now)) {	
				System.out.println(items.get(i).getName()+"'S AUCTION IS NOT AVAILABLE RIGHT NOW");
				continue;
			}
			
			jobj.put("itemId",items.get(i).getId());
			jobj.put("name",items.get(i).getName());
			jobj.put("category",items.get(i).getCategory());
			jobj.put("grade",items.get(i).getGrade());
			jobj.put("startDate",items.get(i).getStartDate());
			jobj.put("image", items.get(i).image.iterator().next().getFilePath());
			
			returningItems.add(jobj);
		}
		
		
		return returningItems;
	}
	
	// "시" 찾기
	@Override
	public List<String> getDistrict() {
		List<District> districts = districtRepository.findAll();
		List<String> returnValue = new ArrayList<>();
		
		// 각 "시"의 이름만 뽑아서 스트링 형태로 리스트에 추가
		for(int i=0; i<districts.size(); ++i) {
			returnValue.add(districts.get(i).getName());
		}
		return returnValue;
	}
	
	// "시-군구" 찾기
	@Override
	public List<String> getSiGunGu(String districtName) {
		
		District district = districtRepository.getDistrictByName(districtName);		
		List<String> returnValue = new ArrayList<>();
		Collection<SiGunGu> siGunGus = district.getSiGunGu();
				
		Iterator<SiGunGu> iter = siGunGus.iterator();
		while(iter.hasNext()) {
			returnValue.add(iter.next().getName());
		}
		
		return returnValue;
	}
	
	@Override
	public BasicResponse getChatLog(long itemId) {
		BasicResponse result = new BasicResponse();
		result.status = true;
		result.data = "아이템 채팅 로그를 조회했습니다.";
		
		JSONObject jobj = new JSONObject(); 

		List<String> userId = redisUtil.getAllLdata(ITEM_CHAT_LOG_USER_ID+itemId);
		List<String> content = redisUtil.getAllLdata(ITEM_CHAT_LOG_USER_CONTENT+itemId);
		List<String> time = redisUtil.getAllLdata(ITEM_CHAT_LOG_USER_TIME+itemId);
		List<String> nickname = redisUtil.getAllLdata(ITEM_CHAT_LOG_USER_NICKNAME+itemId);
		JSONArray jarr = new JSONArray();
		
		for(int i=0; i<userId.size(); ++i) {
			JSONObject temp = new JSONObject();
			
			temp.put("userId", userId.get(i));
			temp.put("userNickname", nickname.get(i));
			temp.put("chatContent", content.get(i));
			temp.put("chatCreatedAt", time.get(i));
			
			jarr.add(temp);
		}
		
		jobj.put("log", jarr);
		
		result.object = jobj;
		return result;
	}
	
	@Override
	public void setStatusById(long itemId, int status) {
		Optional<Item> opt = itemRepository.findById(itemId);
		if(opt.isPresent()) {
			Item item = opt.get();
			
			item.setStatus(status);
			itemRepository.save(item);
		}
	}

}
