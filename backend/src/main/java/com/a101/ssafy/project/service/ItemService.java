package com.a101.ssafy.project.service;

import com.a101.ssafy.project.model.item.Item;

public interface ItemService {
	boolean registerItem(Item item);
	Item getItemById(long id);
}
