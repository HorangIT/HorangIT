package com.a101.ssafy.project.service;

import java.io.IOException;
import java.text.ParseException;

import org.json.simple.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.a101.ssafy.project.model.BasicResponse;
import com.a101.ssafy.project.model.item.RegisterDto;

public interface ItemService {
	BasicResponse registerItem(RegisterDto request, MultipartFile[] multipartFiles) throws ParseException;
	JSONObject getItemById(long id);
}
