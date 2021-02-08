package com.a101.ssafy.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.a101.ssafy.project.model.search.District;

public interface DistrictRepository extends JpaRepository<District, Long>{
		District getDistrictByName(String name);
}
