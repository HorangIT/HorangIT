package com.a101.ssafy.project.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.a101.ssafy.project.model.BasicResponse;
import com.a101.ssafy.project.model.item.Item;
import com.a101.ssafy.project.model.item.RegisterDto;

@Service
public interface ItemService {
    BasicResponse registerItem(RegisterDto request, MultipartFile[] multipartFiles) throws ParseException;

    JSONObject getItemById(long id);

    //Pagination & Filters
    List<JSONObject> getAllPages(List<Item> items);

    List<String> getDistrict();

    List<String> getSiGunGu(String districtName);

    BasicResponse getChatLog(long itemId);

    BasicResponse getChatRoomLog(long itemId);

    boolean setStatusById(long itemId, int status);
}
