package com.a101.ssafy.project.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a101.ssafy.project.model.search.District;
import com.a101.ssafy.project.model.search.SiGunGu;
import com.a101.ssafy.project.repository.DistrictRepository;
import com.a101.ssafy.project.repository.SiGunGuRepository;

@Service
public class SearchServiceImpl implements SearchService{
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
	
	public List<String> getSiGunGu(String name){
		District district = districtRepository.getDistrictByName(name);

		List<String> arr = new ArrayList<>();
		
		Collection<SiGunGu> col = district.getSiGunGu();
		
		Iterator<SiGunGu> iter = col.iterator();
		while(iter.hasNext()) {
			arr.add(iter.next().getName());
		}
		
		return arr;
	}
}
