package com.a101.ssafy.project.service;

import org.json.simple.JSONObject;

import com.a101.ssafy.project.model.item.Item;

public interface ItemService {
	boolean registerItem(Item item);
	JSONObject getItemById(long id);
}
