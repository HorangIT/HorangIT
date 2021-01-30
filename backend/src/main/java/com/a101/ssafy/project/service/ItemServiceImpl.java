package com.a101.ssafy.project.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a101.ssafy.project.model.image.Image;
import com.a101.ssafy.project.model.item.Item;
import com.a101.ssafy.project.repository.ItemRepository;

@Service
public class ItemServiceImpl implements ItemService{
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm"); //날짜 맞출 포맷
	@Autowired
	ItemRepository itemRepository;
	
	@Override
	public boolean registerItem(Item item) {
		itemRepository.save(item);
		
		return true;
	}

	@Override
	public JSONObject getItemById(long id){
		JSONObject jobj = null;
		
		Optional<Item> optional = itemRepository.findById(id);
		if(optional.isPresent()) {
			Item item = optional.get();
			
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
				JSONArray jarr = new JSONArray();
				Iterator<Image> iter = img.iterator();
				
				List<String> list = new ArrayList<>();
				while(iter.hasNext()) {
					String s = iter.next().getFilePath();
					list.add(s);
				}
				
				jarr.add(list);
				jobj.put("filePath", jarr);
			}
		}
		
		return jobj;
	}

}
