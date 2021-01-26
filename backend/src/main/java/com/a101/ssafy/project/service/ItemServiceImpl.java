package com.a101.ssafy.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a101.ssafy.project.model.item.Item;
import com.a101.ssafy.project.repository.ItemRepository;

@Service
public class ItemServiceImpl implements ItemService{

	@Autowired
	ItemRepository itemRepository;
	
	@Override
	public boolean registerItem(Item item) {
		itemRepository.save(item);
		
		return true;
	}

	@Override
	public Item getItemById(long id) {
		return null;
				//itemRepository.getItemById(id);
	}

}
