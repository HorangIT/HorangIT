package com.a101.ssafy.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a101.ssafy.project.dao.ItemDao;
import com.a101.ssafy.project.model.item.Item;

@Service
public class ItemServiceImpl implements ItemService{

	@Autowired
	ItemDao itemDao;
	
	@Override
	public boolean registerItem(Item item) {
		itemDao.save(item);
		return true;
	}

}
