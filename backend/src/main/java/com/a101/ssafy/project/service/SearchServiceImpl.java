package com.a101.ssafy.project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a101.ssafy.project.model.search.District;
import com.a101.ssafy.project.repository.DistrictRepository;
import com.a101.ssafy.project.repository.SiGunGuRepository;

@Service
public class SearchServiceImpl implements SearchService{
	@Autowired
	SiGunGuRepository siGunGuRepository;
	
	@Autowired
	DistrictRepository districtRepository;
	
	public List<String> getDistrict(){
		List<District> list = districtRepository.findAll();
		
		List<String> arr = new ArrayList<>();
		for(int i=0; i<list.size(); ++i) {
			arr.add(list.get(i).getName());
		}
		
		return arr;
	}
}
