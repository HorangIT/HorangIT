package com.a101.ssafy.project.controller;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.a101.ssafy.project.model.item.Item;
import com.a101.ssafy.project.model.search.SearchDto;
import com.a101.ssafy.project.model.search.SearchLocationDto;
import com.a101.ssafy.project.model.search.SearchSpecs;
import com.a101.ssafy.project.repository.SearchRepository;
import com.a101.ssafy.project.service.SearchService;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/search")
public class SearchController {

    @Autowired
    SearchService searchService;

    @Autowired
    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }

    @Autowired
    SearchRepository searchRepository;

    /**
     * 검색할 때
     */
    @GetMapping("/hi")
    public Object searchWithFilter(SearchDto searchDto) {
        Specification<Item> spe = Specification.where(SearchSpecs.price(10000, 20000));
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<Item> p = searchRepository.findAll(spe, pageRequest);

        List<Item> p1 = searchRepository.findAll(spe);

        List<Item> kk = p.getContent();
        for (int i = 0; i < kk.size(); ++i) {
            System.out.println(kk.get(i).getId() + "zz");
        }
        return "hi";
    }


    @GetMapping
    public Object getLocationNames(SearchLocationDto searchLocationDto) {

        JSONObject jobj = new JSONObject();

        // 지역이 비어 있으면 "시"부터 리턴
        if (searchLocationDto.getDistrictName() == null) {
            List<String> districts = searchService.getDistrict();
            jobj.put("districts", districts);
        }
        // null이 아니면 이미 "시"는 있으니 군/구 리턴
        else {
            List<String> gunGu = searchService.getSiGunGu(searchLocationDto.getDistrictName());
            jobj.put("gungu", gunGu);
        }

        return jobj;
    }


}
