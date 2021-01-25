package com.a101.ssafy.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a101.ssafy.project.model.item.Item;
import com.a101.ssafy.project.repository.ItemRepository;

@Service
public class ItemServiceImpl implements ItemService{

	@Autowired
	ItemRepository itemDao;
	
	@Override
	public boolean registerItem(Item item) {
		itemDao.save(item);
		return true;
	}

}
